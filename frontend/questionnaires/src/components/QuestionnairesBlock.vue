<template>
    <div>

        <div v-if="error" class="alert alert-danger" role="alert">
            {{ errorMes }}
            <div class="cardbtn">
                <button @click="relogin" type="button" class="btn btn-outline-primary">Войти</button>
            </div>
        </div>

        <div v-if="empty" class="form">
            <h3 class="form-label">Созданных анкет пока нет :(</h3>
        </div>

        <div class="row scrollable">
        <div v-for="(name, index) in names" v-bind:key="index" class="col-sm-3 cardPad">
            <div class="card">
            <div class="card-body">
                <h5 class="card-title">{{ name }}</h5>
                <div class="cardbtn">
                <button @click="$emit('getTest', name)" type="button" class="btn btn-outline-primary">Открыть</button>
                </div>
            </div>
            </div>
        </div>
        </div>  
    </div>
</template>

<script>


export default {
  name: 'QuestionnairesBlock',
  components: {},
  data() {
    return{
        api: 'http://localhost:8081',

        error: false,
        errorMes: '',
        names: [],
        empty: false
    }
  },
  async mounted() {
    try {
        const res = await fetch(this.api + '/questionnaire/all', {
            method: 'GET',
            headers: new Headers({
            'Authorization': 'Bearer ' + localStorage.getItem('token')
            })
        })
        if (res.status >= 200 & res.status < 300) {
            const names = await res.json()
            if (names.questionnaires.length == 0) {
                this.empty = true
            } else {
                this.names = names.questionnaires
            }
        } else {
            var error = new Error(res.statusText);
            console.log(res.statusText)
            error.response = res;
            throw error
        }
    } catch(e) {
        console.log(e)
        this.raiseError('Для прохождения анкет нужно авторизоваться!')
    }
  },
  methods: {
    raiseError(mes) {
        this.errorMes = mes
        this.error = true
    },
    relogin() {
        this.$router.push('/')
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
</style>