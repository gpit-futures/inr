import Vue from 'vue'
import VueRouter from 'vue-router'
import Page from './components/Page'
import NewPatient from './components/NewPatient'
import NewTreatmentPlan from './components/NewTreatmentPlan'

Vue.use(VueRouter)

const gotoTests = function (to, from, next) {
  next('/tests')
}

const routes = [
  { name: 'NewPatient', path: '/new-patient', component: NewPatient },
  { name: 'NewPlan', path: '/new-plan', component: NewTreatmentPlan },
  { name: 'Tests', path: '/tests', component: Page },
  { path: '*', beforeEnter: gotoTests }
]

export default new VueRouter({
  routes
})
