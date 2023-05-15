import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/layout/Index.vue')
    },
    {
      path: '/Login',
      name: 'login',
      component: () => import('@/views/Login/Login.vue')
    }
    // {
    //   path: '/about',
    //   name: 'about',
    //   // route level code-splitting
    //   // this generates a separate chunk (About.[hash].js) for this route
    //   // which is lazy-loaded when the route is visited.
    //   component: () => import('../views/AboutView.vue')
    // }
  ]
})

router.beforeEach((to, from, next) => {
  const islogin = sessionStorage.getItem('islogin') === 'true'
  if (to.name !== 'login' && !islogin) next({ name: 'login' })
  else next()
})

export default router
