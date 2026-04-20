import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../views/HomePage.vue'
import JournalInfo from '../views/JournalInfo.vue'
import Papers from '../views/Papers.vue'
import Publications from '../views/Publications.vue'
import UserGuide from '../views/UserGuide.vue'
import Login from '../views/Login.vue'
import SubmitArticle from '../views/SubmitArticle.vue'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomePage
  },
  {
    path: '/about',
    name: 'AboutJournal',
    component: JournalInfo
  },
  {
    path: '/papers',
    name: 'Papers',
    component: Papers
  },
  {
    path: '/publications',
    name: 'Publications',
    component: Publications
  },
  {
    path: '/guide',
    name: 'UserGuide',
    component: UserGuide
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/submit',
    name: 'SubmitArticle',
    component: SubmitArticle,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// 简单的路由守卫示例
router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('isAuthenticated')
  
  if (to.meta.requiresAuth && !isAuthenticated) {
    next('/login')
  } else {
    next()
  }
})

export default router
