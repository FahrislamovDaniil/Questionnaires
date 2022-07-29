<template>
    <div>

      <div>
        <NavigationTab
        @admin="adminGet" 
        @user="userGet"/>
      </div>

      <div v-if="showList" class="cardblock">

        <div v-if="error" class="alert alert-danger secondary" role="alert">
          {{ errorMes }}
          <div class="cardbtn">
            <button @click="relogin" type="button" class="btn btn-outline-primary">Войти</button>
          </div>
        </div>

        <div v-if="empty" class="form">
            <h3 class="form-label">Пройденных анкет пользователя {{ username }} пока нет :(</h3>
        </div>

        <h2 v-if="showList & header" class="second secondary">Результаты пользователя {{ username }}</h2>

        <div class="row scrollable">
        <div v-for="(attempt, index) in attempts" v-bind:key="index" class="col-sm-3 cardPad">
            <div class="card">
            <div class="card-body">
                <h5 class="card-title">{{ attempt.questionnaireDto.name }}</h5>
                <p class="card-text">Набранные баллы: {{ attempt.attemptDto.score }}</p>
                <div class="cardbtn">
                <button @click="getTest(index)" type="button" class="btn btn-outline-primary">Открыть</button>
                </div>
            </div>
            </div>
        </div>
        </div>  
    </div>

    <div v-if="showResult" class="form scrollable full">
        <div>
            <form>
                <h3 class="form-label">{{ current.questionnaireDto.name }}. Баллы: {{ current.attemptDto.score }}. Попытка №{{ current.attemptDto.tries }}</h3>
                <div v-for="(question, qindex) in current.questionnaireDto.questions" v-bind:key="qindex" class="cardbtn">
                    <h4 class="form-label">{{ question.text }}</h4>
                    <div v-for="(answer, aindex) in question.answers" v-bind:key="aindex" class="form-check">
                        <input :disabled="true" v-model="current.attemptDto.answers" :value="{id:answer.id, text:attempts[ind].questionnaireDto.questions[qindex].answers[aindex].text}" class="form-check-input" type="checkbox" id="flexCheckDefault">
                        <label :disabled="true"  class="form-check-label" for="flexCheckDefault">
                            {{ answer.text }}
                        </label>
                    </div>
                </div>
            </form>
        </div>
    </div>

    <h2 v-if="rating" class="cardblock second secondary">Место в рейтинге пользователя {{ byUser.username }}: #{{ users.indexOf(byUser) + 1 }}</h2>

    <div v-if="rating" class="form scrollable full">
      
      <table class="table">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Имя на сайте</th>
            <th scope="col">Баллы</th>
          </tr>
        </thead>
        <tbody>
          <tr @click="changeUser(user.username)" v-for="(user, index) in users" v-bind:key="index" class="pointer">
            <th scope="row">{{ index + 1 }}</th>
            <td>{{ user.username }}</td>
            <td>{{ user.score }}</td>
          </tr>
        </tbody>
      </table>

    </div>

    <div v-if="rating" class="secondary">
        <button @click="back" type="button" class="btn btn-primary button-bott">Назад</button>
    </div>

    <div v-if="showResult" class="secondary">
      <button @click="back" type="button" class="btn btn-primary button-bott">Назад</button>
    </div>

  </div>

  <div v-if="ratingButton" class="button-bott">
      <button @click="showRating" type="button" class="btn btn-primary">Рейтинг</button>
  </div>
</template>

<script>
import NavigationTab from '@/components/NavigationTab.vue'

export default {
  name: 'AttemptCheck',
  components: {NavigationTab},
  data() {
    return {
      api: 'http://localhost:8081',

      error: false,
      errorMes: '',
      header: true,

      ratingButton: false,
      rating: false,
      showList: true,
      showResult: false,
      empty: false,

      username: '',
      byUser: {},
      users: [],

      ind: -1,
      current: {},

      attempts: []
    }
  },
  async mounted() {
    this.username = localStorage.getItem('username')

    this.checking()
  },
  methods: {
    async getTest(ind) {
      this.ind = ind
      var a = JSON.parse(JSON.stringify(this.attempts[ind]))
      var allow = false

      try {
          const res = await fetch(this.api + '/questionnaire/admin/get/test', {
            method: 'GET',
            headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
          })
          if (res.status >= 200 & res.status < 300) {
            allow = true
          } else {
            var error = new Error(res.statusText);
            console.log(res.statusText)
            error.response = res;
            throw error
          }
        } catch(e) {
          allow = false
        }

      if (a.attemptDto.tries >= a.questionnaireDto.tries || allow)
      a.questionnaireDto.questions.forEach(que => {
        que.answers.forEach(ans => {
          if (ans.correct) {
            ans.text = ans.text.concat(' (верный)')
          }
        })
      })

      this.current = a
    
      this.ratingButton = false
      this.showList = false
      this.showResult = true
    },
    async showRating() {
      
      try {
        const res = await fetch(this.api + '/user/all', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          }
        })

        if (res.status >= 200 & res.status < 300) {
          const users = await res.json()
          this.users = users.users

          this.users.forEach(u => {
            if (u.username == this.username) {
              this.byUser = u
            }
          })
          this.ratingButton = false
          this.showList = false
          this.rating = true
        } else {
          var er = new Error(res.statusText);
          console.log(res.statusText)
          er.response = res;
          throw er
        }
      } catch(e) {
        this.$router.go(0)
      }
    },
    async changeUser(username) {
      if (username != localStorage.getItem('username')) {
        try {
          const res = await fetch(this.api + '/questionnaire/admin/get/test', {
            method: 'GET',
            headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
          })
          if (res.status >= 200 & res.status < 300) {
            this.username = username

            this.checking()

            this.ratingButton = true
            this.showList = true
            this.showResult = false
            this.rating = false
            this.error = false
            this.header = true
          } else {
            var error = new Error(res.statusText);
            console.log(res.statusText)
            error.response = res;
            throw error
          }
        } catch(e) {
          this.ratingButton = true
          this.showList = true
          this.showResult = false
          this.rating = false
          this.error = false
          this.header = false

          this.raiseError('Для просмотра результатов других пользователей нужно обладать правами администратора!')
        }
      } else {
        this.username = username

        this.checking()

        this.ratingButton = true
        this.showList = true
        this.showResult = false
        this.rating = false
        this.error = false
        this.header = true
      }
    },
    async checking() {
      this.empty = false
      this.attempts = []
      try {
        const res = await fetch(this.api + '/attempt/all/' + this.username, {
          method: 'GET',
          headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
          })
        })
        if (res.status >= 200 & res.status < 300) {
          const attempts = await res.json()
          if (attempts.attempts.length == 0) { 
            this.empty = true
            this.ratingButton = true
          } else {
            this.attempts = attempts.attempts
            this.ratingButton = true
          }
        } else {
          var error = new Error(res.statusText);
          console.log(res.statusText)
          error.response = res;
          throw error
        }
      } catch(e) {
        this.raiseError('Для просмотра результатов нужно авторизоваться!')
      }
    },
    back() {
      this.ratingButton = true
      this.showList = true
      this.showResult = false
      this.rating = false
      this.error = false
    },
    raiseError(mes) {
      this.header = !this.header
      this.errorMes = mes
      this.error = true
    },
    relogin() {
      this.$router.push('/')
    },
    adminGet() {},
    userGet() {}
  }
}
</script>