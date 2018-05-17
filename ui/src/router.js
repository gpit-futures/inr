import Vue from 'vue'
import VueRouter from 'vue-router'
import Page from './components/Page'
import NewPatient from './components/NewPatient'
import NewTreatmentPlan from './components/NewTreatmentPlan'
import LandingPage from './components/LandingPage'

Vue.use(VueRouter)

const gotoLanding = function (to, from, next) {
  next({name: 'LandingPage'})
}

const routes = [
  { name: 'NewPatient', path: '/new-patient', component: NewPatient },
  { name: 'NewPlan', path: '/new-plan', component: NewTreatmentPlan },
  { name: 'Tests', path: '/tests', component: Page },
  { name: 'LandingPage', path: '/', component: LandingPage },
  { path: '*', beforeEnter: gotoLanding }
]

export default new VueRouter({
  routes
})
