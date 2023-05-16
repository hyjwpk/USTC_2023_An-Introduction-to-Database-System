import "@/assets/css/main.css";

import { createApp } from 'vue'

import App from '@/App.vue'
import router from '@/router'
import store from "@/store"

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Icons from "@/components/Icons.vue";

import '@/mock/index.js'

const app = createApp(App)

app.use(router)
app.use(store)
app.use(ElementPlus)
app.component("Icons", Icons)
app.mount('#app')
