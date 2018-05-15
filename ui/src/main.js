import Vue from 'vue'
import Vuex from 'vuex'
import App from './App'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'
import store from './store/store'
import { DwClientConnector } from 'dw-client-connector'

Vue.config.productionTip = false
Vue.use(Vuetify)
Vue.use(Vuex)

DwClientConnector.init(window.location.origin, 'http://localhost:3000')

/* eslint-disable no-new */
new Vue({
  el: '#app',
  store,
  render: h => h(App)
})
