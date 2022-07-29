<template>

    <h2 v-if="showList" class="second secondary">Выберите анкету для прохождения</h2>

    <div v-if="showSecurityError & showList" class="alert alert-danger secondary" role="alert">
      {{ securityError }}
    </div>

    <div v-if="showList">
      <QuestionnairesBlock
      @getTest="getTest"/>
    </div>

    <div v-if="!showList" class="form scrollable full">
        <div>
            <form>
                <h3 class="form-label">{{ test.name }}. Всего попыток: {{ test.tries }}</h3>
                <div v-for="(question, index) in test.questions" v-bind:key="index" class="cardbtn">
                    <h4 class="form-label">{{ question.text }}</h4>
                    <div v-for="(answer, index) in question.answers" v-bind:key="index" class="form-check">
                        <input v-model="selected" :value="answer" class="form-check-input" type="checkbox" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                            {{ answer.text }}
                        </label>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="error">
      <label v-if="!showList & showError" class="form-check-label">{{ error }}</label>
    </div>
    <div v-if="!showList" class="secondary">
      <button @click="sendAttempt" type="button" class="btn btn-primary button-bott">Отправить</button>
      <button @click="back" type="button" class="btn btn-primary button-bott second">Назад</button>
    </div>
</template>

<script>
import QuestionnairesBlock from '@/components/QuestionnairesBlock.vue'

export default {
  name: 'TakeQuestionnaires',
  components: {QuestionnairesBlock},
  data() {
    return{
      api: 'http://localhost:8081',

      showList: true,
      showError: false,
      error: '',
      showSecurityError: false,
      securityError: '',
      selected:[],
      test: {},
      attempt: {}
    }
  },
  methods: {
    async getTest(name) {
      this.selected = []

      try {
        const res = await fetch(this.api + '/questionnaire/get/' + name, {
            method: 'GET',
            headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
        })
        if (res.status >= 200 & res.status < 300) {
            const test = await res.json()
            this.test = test

            try {
              const res = await fetch(this.api + '/attempt/all/' + localStorage.getItem('username'), {
                method: 'GET',
                headers: new Headers({
                  'Authorization': 'Bearer ' + localStorage.getItem('token')
                })
              })
              if (res.status >= 200 & res.status < 300) {
                const attempts = await res.json()
                var exist = false
                attempts.attempts.forEach(a => {
                  if (a.questionnaireDto.id == this.test.id) {
                    exist = true
                    this.attempt = a.attemptDto
                    if (a.attemptDto.tries >= this.test.tries){
                      this.securityError = 'Вы достигли лимита попыток для данного теста!'
                      this.showSecurityError = true
                    } else {
                      this.showError = false
                      this.showList = false
                    }
                  }
                })
                if (!exist) {
                  this.showError = false
                  this.showList = false
                  this.attempt = {}
                }
                if (attempts.attempts.length == 0) {
                  this.showError = false
                  this.showList = false
                  this.attempt = {}
                }
              } else {
                var error = new Error(res.statusText);
                console.log(res.statusText)
                error.response = res;
                throw error
              }
            } catch(e) {
              this.$router.go(0)
            }
        } else {
            var e = new Error(res.statusText);
            console.log(res.statusText)
            e.response = res;
            throw e
        }
      } catch(e) {
        this.$router.go(0)
      }
    },
    async sendAttempt() {
      var valid = true
      this.test.questions.forEach(que => {
        var incl = false
        this.selected.forEach(s => {
          if(que.answers.includes(s)) {
            incl = true
          }
        })
        if(!incl) {
          valid = false
        }
      })

      if (!valid) {
        this.error = 'Выберите хотя бы один ответ для каждого вопроса!'
        this.showError = true
      } else {
        var attempt = {}
        if (this.attempt === {}) {
          attempt = {
            user: localStorage.getItem('username'),
            tries: 0,
            answers: this.selected
          }

          try {
            const res = await fetch(this.api + '/attempt/add', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(attempt)
          })

            if (res.status >= 200 & res.status < 300) {
              this.selected = []
              this.attempt = {}
              this.showList = true
              this.showSecurityError = false
            } else {
              var e = new Error(res.statusText);
              console.log(res.statusText)
              e.response = res;
              throw e
            }
          } catch(e) {
            this.$router.go(0)
          }
        } else {
          attempt = {
            id: this.attempt.id,
            user: localStorage.getItem('username'),
            score: this.attempt.score,
            tries: this.attempt.tries,
            answers: this.selected
          }

          try {
          const res = await fetch(this.api + '/attempt/update', {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(attempt)
          })

            if (res.status >= 200 & res.status < 300) {
              this.selected = []
              this.attempt = {}
              this.showList = true
              this.showSecurityError = false
            } else {
              var er = new Error(res.statusText);
              console.log(res.statusText)
              er.response = res;
              throw er
            }
          } catch(e) {
            this.$router.go(0)
          }
        }
      }
    },
    back() {
      this.showError = false
      this.showList = true
      this.showSecurityError = false
    }
  },
}
</script>

<style>
.cardbtn {
    padding-top: 15px;
}
.cardPad {
    padding-top: 25px;
}
.form{
    padding: 50px;
    position: fixed; top: 50%; left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}
.secondary{
    margin-top: 20px ;
}
</style>