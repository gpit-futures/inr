import Vue from 'vue'
import Vuex from 'vuex'
import mutators from './mutators'

Vue.use(Vuex)

console.log('----store')
export default new Vuex.Store({
  state: {
    patient: null,
    patientContext: null,
    treatmentPlan: null
  },
  mutations: {
    [mutators.SET_PATIENT] (state, patient) {
      console.log('SET_PATIENT')
      state.patient = patient
    },
    [mutators.SET_PATIENT_CONTEXT] (state, patient) {
      console.log('SET_PATIENT_CONTEXT')
      state.patientContext = patient
    },
    [mutators.SET_TREATMENT_PLAN] (state, treatmentPlan) {
      console.log('SET_TREATMENT_PLAN')
      state.treatmentPlan = treatmentPlan
    },
    [mutators.ADD_TEST_TO_PLAN] (state, test) {
      console.log('ADD_TEST_TO_PLAN')
      state.treatmentPlan.items.unshift(test)
    }
  }
})
