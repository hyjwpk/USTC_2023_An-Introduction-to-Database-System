import { createRouter, createWebHistory } from 'vue-router'
import { routes } from "@/router/router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...routes]
})

router.beforeEach((to, from, next) => {
  const islogin = sessionStorage.getItem('islogin') === 'true'
  if (islogin) {
    if (to.name === 'login') {
      next({ name: 'Layout' })
    } else {
      next()
    }
  } else {
    if (to.name === 'login') {
      next()
    } else {
      next({ name: 'login' })
    }
  }
})

export default router
