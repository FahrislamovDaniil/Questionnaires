<template>

    <div v-if="error" class="alert alert-danger" role="alert">
        {{ errorMes }}
    </div>

    <div class="form">
        <form v-if="showLogin">
        <h3 class="form-label">Вход</h3>
        <div class="mb-3">
            <label for="exampleInputUsername" class="form-label">Имя на сайте</label>
            <input v-model="usernameLogin" type="username" class="form-control" id="exampleInputUsername">
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Пароль</label>
            <input v-model="passwordLogin" type="password" class="form-control" id="exampleInputPassword1">
        </div>
        <button @click="sendLogin" type="button" class="btn btn-primary">Войти</button>
        <button @click="switchToReg" type="button" class="btn btn-primary second">
        Нет аккаунта?
        </button>
        </form>

        <form v-if="showReg">
        <h3 class="form-label">Регистрация</h3>
        <div class="mb-3">
            <label for="exampleInputEmail1" class="form-label">Электронная почта</label>
            <input v-model="email" type="email" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp">
            <div id="emailHelp" class="form-text">На почту будет выслано уведомление о регистрации.</div>
        </div>
        <div class="mb-3">
            <label for="exampleInputUsername" class="form-label">Имя на сайте</label>
            <input v-model="usernameReg" type="username" class="form-control" id="exampleInputUsername" aria-describedby="usernameHelp">
            <div id="usernameHelp" class="form-text">Буквы и цифры без специальных символов.</div>
        </div>
        <div class="mb-3">
            <label for="exampleInputPassword1" class="form-label">Пароль</label>
            <input v-model="passwordReg" type="password" class="form-control" id="exampleInputPassword1">
        </div>
        <button @click="sendReg" type="button" class="btn btn-primary">Регистрация</button>
        <button @click="switchToLogin" type="button" class="btn btn-primary second">
        Уже есть аккаунт?
        </button>
        </form>
    </div>
</template>

<script>

export default {
  name: 'LoginReg',
  components: {},
  data() {
    return{
        api: 'http://localhost:8081',

        error: false,
        errorMes: '',

        showReg: true,
        showLogin: false,

        email:'',
        usernameReg:'',
        passwordReg:'',
        usernameLogin:'',
        passwordLogin:''
    }
  },
  mounted() {
    localStorage.removeItem('token')
  },
  methods: {
    switchToLogin() {
        this.showReg = false
        this.showLogin = true
        this.error = false
    },
    switchToReg() {
        this.showReg = true
        this.showLogin = false
        this.error = false
    },

    async sendReg() {
        var regexName = new RegExp("^[a-zA-Z0-9]+$");
        var regexEmail = new RegExp("^[a-zA-Z0-9@.]+$");
        if (this.email == '' | this.usernameReg == '' | this.passwordReg == '' | !regexName.test(this.usernameReg) | !regexEmail.test(this.email)) {
            this.raiseError('Введите корректные данные для регистрации!')
        } else {
            const res = await fetch(this.api + '/user/get/username/' + this.usernameReg, {
                method: 'GET'
            })
            const userInfo = await res.json()

            if (!userInfo.username) {
                const eRes = await fetch(this.api + '/user/get/email/' + this.email, {
                    method: 'GET'
                })
                const eUserInfo = await eRes.json()

                if (!eUserInfo.email) {
                    await fetch(this.api + '/user/add', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            email: this.email,
                            username: this.usernameReg,
                            password: this.passwordReg
                        })
                    })

                    this.switchToLogin()
                } else {
                    this.raiseError('Данная почта уже зарегестрирована на сайте!')
                }
            } else {
                this.raiseError('Пользователь с таким именем уже существует!')
            }            
        }
    },
    async sendLogin() {
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        if (this.usernameLogin == '' | this.passwordLogin == '' | !regex.test(this.usernameLogin)) {
            this.raiseError('Введите корректные данные для входа!')
        } else {
            try {
                const res = await fetch(this.api + '/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        username: this.usernameLogin,
                        password: this.passwordLogin
                    })
                })
                if (res.status >= 200 & res.status < 300) {
                    const token = await res.json()
                    localStorage.setItem('token', token.token)
                    localStorage.setItem('username', this.usernameLogin)
                    this.$router.push('/tests')
                } else {
                    var error = new Error(res.statusText);
                    console.log(res.statusText)
                    error.response = res;
                    throw error
                }
            } catch (e) {
                this.raiseError('Пользователь с таким именем и паролем не зарегистрирован!')
            }
        }
    },
    raiseError(mes) {
        this.errorMes = mes
        this.error = true
    }
  }
}
</script>

<style>
.form{
    padding: 50px;
    position: fixed; top: 50%; left: 50%;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
}

.second{
    margin-left: 10px ;
}
</style>