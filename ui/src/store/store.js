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
    /* The patient stored in the INR system */
    [mutators.SET_PATIENT] (state, patient) {
      state.patient = patient
    },
    /* The patient context passed in from the PCaaP frame */
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
