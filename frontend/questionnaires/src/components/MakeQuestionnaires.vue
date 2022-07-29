<template>

    <h2 v-if="showList" class="second secondary">Редактировать или создать анкету</h2>

    <div v-if="showSecurityError" class="alert alert-danger secondary" role="alert">
      {{ securityError }}
    </div>

    <div v-if="showList">
      <QuestionnairesBlock
      @getTest="getTest"/>
    </div>

    <div v-if="showEdit" class="form scrollable full">
        <div>
            <form>
              <label for="inputName" class="form-label">Название</label>
              <input v-model="test.name" type="email" class="form-control" id="inputName">

              <label for="inputTries" class="form-label">Количество попыток</label>
              <input v-model="test.tries" type="email" class="form-control" id="inputTries">

              <div v-for="(question, index) in test.questions" v-bind:key="index">
                <label for="inputQueText" class="form-label secondary">Текст вопроса</label>
                <input v-model="question.text" type="email" class="form-control" id="inputQueText">

                <div v-for="(answer, ind) in question.answers" v-bind:key="ind" class="shift">
                  <label for="inputAnsText" class="form-label secondary">Текст ответа</label>
                  <input v-model="answer.text" type="email" class="form-control" id="inputAnsText">
                  <input v-model="answer.correct" class="form-check-input secondary" type="checkbox" id="flexCheckDefault">
                  <label class="form-check-label secondary second" for="flexCheckDefault">Верный</label>
                </div>

                <div class="secondary">
                  <button @click="newAnswer(index)" type="button" class="btn btn-primary shift">Добавить ответ</button>
                </div>
              </div>
              <div class="secondary">
                <button @click="newQuestion" type="button" class="btn btn-primary">Добавить вопрос</button>
              </div>
            </form>
        </div>
    </div>
    <div class="error">
      <label v-if="showEdit & showError" class="form-check-label">{{ error }}</label>
    </div>
    <div v-if="showEdit">
      <button @click="update" type="button" class="btn btn-primary button-bott">Отправить</button>
      <button @click="back" type="button" class="btn btn-primary button-bott second">Назад</button>
    </div>

    <div v-if="showNew" class="form scrollable full">
        <div>
            <form>
              <label for="inputName" class="form-label">Название</label>
              <input v-model="test.name" type="email" class="form-control" id="inputName">

              <label for="inputTries" class="form-label">Количество попыток</label>
              <input v-model="test.tries" type="email" class="form-control" id="inputTries">

              <div v-for="(question, index) in test.questions" v-bind:key="index">
                <label for="inputQueText" class="form-label secondary">Текст вопроса</label>
                <input v-model="question.text" type="email" class="form-control" id="inputQueText">

                <div v-for="(answer, ind) in question.answers" v-bind:key="ind" class="shift">
                  <label for="inputAnsText" class="form-label secondary">Текст ответа</label>
                  <input v-model="answer.text" type="email" class="form-control" id="inputAnsText">
                  <input v-model="answer.correct" class="form-check-input secondary" type="checkbox" id="flexCheckDefault">
                  <label class="form-check-label secondary second" for="flexCheckDefault">Верный</label>
                </div>

                <div class="secondary">
                  <button @click="newAnswer(index)" type="button" class="btn btn-primary shift">Добавить ответ</button>
                  <button @click="deleteAnswer(index)" type="button" class="btn btn-primary second shift">Удалить</button>
                </div>
              </div>
              <div class="secondary">
                <button @click="newQuestion" type="button" class="btn btn-primary">Добавить вопрос</button>
                <button @click="deleteQuestion" type="button" class="btn btn-primary second">Удалить</button>
              </div>
            </form>
        </div>
    </div>

    <div class="error">
      <label v-if="showNew & showError" class="form-check-label">{{ error }}</label>
    </div>
    <div v-if="showNew">
      <button @click="sendNew" type="button" class="btn btn-primary button-bott">Отправить</button>
      <button @click="back" type="button" class="btn btn-primary button-bott second">Назад</button>
    </div>

  <div v-if="newButton" class="button-bott">
    <button @click="newTest" type="button" class="btn btn-primary">Создать</button>
  </div>
</template>

<script>
import QuestionnairesBlock from '@/components/QuestionnairesBlock.vue'

export default {
  name: 'MakeQuestionnaires',
  components: {QuestionnairesBlock},
  data() {
    return{
      api: 'http://localhost:8081',

      showList: false,
      showNew: false,
      showEdit: false,
      newButton: false,

      showSecurityError: false,
      securityError: '',

      showError: false,
      error:'',

      test: {}
    }
  },
  async mounted() {
    try {
        const res = await fetch(this.api + '/questionnaire/admin/get/test', {
            method: 'GET',
            headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
        })
        if (res.status >= 200 & res.status < 300) {
            this.showList = true
            this.newButton = true
        } else {
            var error = new Error(res.statusText);
            console.log(res.statusText)
            error.response = res;
            throw error
        }
    } catch(e) {
        this.raiseError('Смените пользователя! Для создания анкет нужно обладать правами администратора!')
    }
  },
  methods: {
    async getTest(name) {
      try {
        const res = await fetch(this.api + '/questionnaire/admin/get/' + name, {
            method: 'GET',
            headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
        })
        if (res.status >= 200 & res.status < 300) {
          const test = await res.json()
          this.test = test
          this.newButton = false
          this.showList = false
          this.showEdit = true
        } else {
            var error = new Error(res.statusText);
            console.log(res.statusText)
            error.response = res;
            throw error
        }
      } catch(e) {
        this.showList = false,
        this.showNew = false,
        this.showEdit = false,
        this.newButton = false,
        this.raiseError('Смените пользователя! Для создания анкет нужно обладать правами администратора!')
      }
    },
    newTest() {
      this.test = {
        id: 0,
        name: '',
        tries: 0,
        questions: []
      }

      this.showNew = true
      this.showList = false
      this.newButton = false
    },
    newQuestion() {
      this.test.questions.push({
        id: 0,
        questionnaire: 0,
        text: '',
        answers: []
      })
    },
    deleteQuestion() {
      this.test.questions.pop()
    },
    newAnswer(index) {
      this.test.questions[index].answers.push({
        id: 0,
        question: 0,
        text: '',
        correct: false
      })
    },
    deleteAnswer(index) {
      this.test.questions[index].answers.pop()
    },
    validate() {
      this.showError = false
      var notFill = false
      if (this.test.name == '' || isNaN(parseInt(this.test.tries, 10)) || 
      parseInt(this.test.tries, 10) < 1 || parseInt(this.test.tries, 10) > 2 || 
      this.test.questions.isEmpty) {
        notFill = true
      } 
      this.test.questions.forEach(que => {
        if (que.text == '' || que.answers.length < 2) {
          notFill = true
        }

        var exists = false
        que.answers.forEach(ans => {
          if (ans.text == '') {
            notFill = true
          }

          if(ans.correct == true) {
            exists = true
          }
        })
        if(!exists) {
          notFill = true
        }

      })
      return notFill
    },
    async sendNew() {
      if (this.validate()) {
        this.error = 'Проверьте правильность введенных данных и повторите попытку!'
        this.showError = true
      } else {
        const newTest = {
          name: this.test.name,
          tries: parseInt(this.test.tries, 10)
        }

        try {
          const res = await fetch(this.api + '/questionnaire/admin/add', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(newTest)
          })

          if (res.status >= 200 & res.status < 300) {
            const questionnaire = await res.json()
            const ques = this.test.questions
            for (const que of ques) {
              const newQue = {
                questionnaire: questionnaire.id,
                text: que.text
              }

              try {
                const resQue = await fetch(this.api + '/question/admin/add', {
                  method: 'POST',
                  headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                  },
                  body: JSON.stringify(newQue)
                })

                if (resQue.status >= 200 & resQue.status < 300) {
                  const question = await resQue.json()
                  const anses = que.answers
                  for (const ans of anses) {
                    const newAns = {
                      question: question.id,
                      text: ans.text,
                      correct: ans.correct
                    }

                    try {
                      const resAns = await fetch(this.api + '/answer/admin/add', {
                        method: 'POST',
                        headers: {
                          'Content-Type': 'application/json',
                          'Authorization': 'Bearer ' + localStorage.getItem('token')
                        },
                        body: JSON.stringify(newAns)
                      })

                      if (resAns.status >= 200 & resAns.status < 300) {
                        resAns
                      } else {
                        var err = new Error(resAns.statusText);
                        console.log(resAns.statusText)
                        err.response = resAns;
                        throw err
                      }
                    } catch(e) {
                      this.back()
                    }
                  }
                } else {
                  var er = new Error(resQue.statusText);
                  console.log(resQue.statusText)
                  er.response = resQue;
                  throw er
                }
              } catch(e) {
                this.back()
              }
            }
          } else {
            var e = new Error(res.statusText);
            console.log(res.statusText)
            e.response = res;
            throw e
          }
        } catch(e) {
          this.back()
        }
        this.back()
      }
    },
    async update() {
      if (this.validate()) {
        this.error = 'Проверьте правильность введенных данных и повторите попытку!'
        this.showError = true
      } else {
        const newTest = {
          id: this.test.id,
          name: this.test.name,
          tries: parseInt(this.test.tries, 10)
        }

        try {
          const res = await fetch(this.api + '/questionnaire/admin/add', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer ' + localStorage.getItem('token')
            },
            body: JSON.stringify(newTest)
          })

          if (res.status >= 200 & res.status < 300) {
            const questionnaire = await res.json()
            const ques = this.test.questions
            for (const que of ques) {
              var newQue = {}
              if(que.id == 0) {
                newQue = {
                  questionnaire: questionnaire.id,
                  text: que.text
                }
              } else {
                newQue = {
                  id: que.id,
                  questionnaire: questionnaire.id,
                  text: que.text
                }
              }
              try {
                const resQue = await fetch(this.api + '/question/admin/add', {
                  method: 'POST',
                  headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                  },
                  body: JSON.stringify(newQue)
                })

                if (resQue.status >= 200 & resQue.status < 300) {
                  const question = await resQue.json()
                  const anses = que.answers
                  for (const ans of anses) {
                    var newAns = {}
                    if(ans.id == 0) {
                      newAns = {
                        question: question.id,
                        text: ans.text,
                        correct: ans.correct
                      }
                    } else {
                      newAns = {
                        id: ans.id,
                        question: question.id,
                        text: ans.text,
                        correct: ans.correct
                      }          
                    } 

                    try {
                      const resAns = await fetch(this.api + '/answer/admin/add', {
                        method: 'POST',
                        headers: {
                          'Content-Type': 'application/json',
                          'Authorization': 'Bearer ' + localStorage.getItem('token')
                        },
                        body: JSON.stringify(newAns)
                      })

                      if (resAns.status >= 200 & resAns.status < 300) {
                        newAns
                      } else {
                        var err = new Error(resAns.statusText);
                        console.log(resAns.statusText)
                        err.response = resAns;
                        throw err
                      }
                    } catch(e) {
                      this.back()
                    }
                  }
                } else {
                  var er = new Error(resQue.statusText);
                  console.log(resQue.statusText)
                  er.response = resQue;
                  throw er
                }
              } catch(e) {
                this.back()
              }
            }
          } else {
            var e = new Error(res.statusText);
            console.log(res.statusText)
            e.response = res;
            throw e
          }
        } catch(e) {
          this.back()
        }
        this.back()
      }
    },
    back() {
      this.newButton = true
      this.showList = true
      this.showEdit = false
      this.showNew = false
    },
    raiseError(mes) {
        this.securityError = mes
        this.showSecurityError = true
    }
  }
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
.button-bott {
    position: absolute;
    bottom: 10px;
    left: 50%;
    margin-left: -104.5px;
    margin-bottom: 25px;
}
.shift{
    margin-left: 30px ;
}
.error{
  text-align: center;
  margin-top: 15px;
}
</style>