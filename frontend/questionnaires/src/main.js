import * as VueRouter from 'vue-router';
import { createApp } from 'vue' 
import App from './App.vue'
import "bootstrap/dist/css/bootstrap.css"

import QuestionnairesMain from '@/components/QuestionnairesMain'
import LoginReg from '@/components/LoginReg'
import AttemptCheck from '@/components/AttemptCheck'

const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes: [
        {
            path: "/",
            component: LoginReg
        },
        {
            path: "/tests",
            component: QuestionnairesMain
        },
        {
            path: "/attempts",
            component: AttemptCheck
        }
    ]
})

createApp(App).use(router).mount('#app', router)

import "bootstrap/dist/js/bootstrap.js"