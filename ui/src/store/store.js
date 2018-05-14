import Vue from 'vue'
import Vuex from 'vuex'
import mutators from './mutators'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    patient: null,
    patientContext: null,
    treatmentPlan: null
  },
  mutations: {
    [mutators.SET_PATIENT] (state, patient) {
      state.patient = patient
    },
    [mutators.SET_PATIENT_CONTEXT] (state, patient) {
      state.patientContext = patient
    },
    [mutators.SET_TREATMENT_PLAN] (state, treatmentPlan) {
      state.treatmentPlan = treatmentPlan
    },
    [mutators.ADD_TEST_TO_PLAN] (state, test) {
      state.treatmentPlan.items.unshift(test)
    }
  }
})
