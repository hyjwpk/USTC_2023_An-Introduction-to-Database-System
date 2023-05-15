import { createRouter, createWebHistory } from 'vue-router'
import { routes } from "@/router/router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [...routes]
})

router.beforeEach((to, from, next) => {
  const islogin = sessionStorage.getItem('islogin') === 'true'
  if (to.name !== 'login' && !islogin) next({ name: 'login' })
  else next()
})

export default router
