import Vue from 'vue'
import VueRouter from 'vue-router'
import Climbers from '../views/Climbers.vue'
import Searches from '../views/Searches.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/climbers',
    name: 'climbers',
    component: Climbers
  },
  {
    path: '/searches',
    name: 'searches',
    component: Searches
  },
  {
    path: '/',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
