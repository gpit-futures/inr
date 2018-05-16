import Vue from 'vue'
import Vuex from 'vuex'
import mutators from './mutators'
import { ukDateToInternational } from '../utilities'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    patient: null,
    patientContext: null,
    treatmentPlan: null,
    static: {
      inrValues: ['1.8', '1.9', '2.0', '2.1', '2.2', '2.3', '2.4', '99']
    }
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
      let sortDate = ukDateToInternational(test.testDate)
      const data = Object.assign({sortDate}, test)
      state.treatmentPlan.items.push(data)
      state.treatmentPlan.items.sort((p1, p2) => {
        return p2.sortDate.localeCompare(p1.sortDate)
      })
    }
  }
})
