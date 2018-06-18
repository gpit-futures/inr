import Vue from 'vue'
import Vuex from 'vuex'
import mutators from './mutators'
import router from '../router'
import { getPatient } from '../api/patient'
import { getTreatmentPlan } from '../api/treatmentPlan'
import { getObservation } from '../api/observation'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    patient: null,
    patientContext: null,
    treatmentPlans: [],
    selectedPlan: null,
    selectedObservation: null,
    observations: [],
    static: {
      inrValues: [1.8, 1.9, 2.0, 2.1, 2.2, 2.3, 2.4, 99]
    },
    token: null
  },
  mutations: {
    /* The patient stored in the INR system - get the patient from the INR middleware - if none exists promt user.
    axios.get('../patient/' + patient.nhsNumber) */
    async [mutators.SET_PATIENT] (state, patient) {
      if (patient) {
        state.patient = await getPatient(patient)
      } else {
        state.patient = patient
      }
    },
    async [mutators.SET_PATIENT_INR] (state, patient) {
      state.patient = await getPatient(patient)
      if (state.patient) {
        state.treatmentPlans = await getTreatmentPlan(state.patient)
        state.selectedPlan = state.treatmentPlans[0]
      }

      if (state.patient == null) {
        router.push({name: 'NewPatient'})
      } else if (state.treatmentPlans.length < 1) {
        router.push({ name: 'NewPlan' })
      } else {
        router.push({name: 'Tests'})
      }
    },
    /* The patient context passed in from the PCaaP frame */
    [mutators.SET_PATIENT_CONTEXT] (state, patient) {
      state.patientContext = patient
    },
    [mutators.SET_TREATMENT_PLAN] (state, treatmentPlans) {
      state.treatmentPlans = treatmentPlans
    },
    [mutators.SET_SELECTED_PLAN] (state, selectedPlan) {
      state.selectedPlan = selectedPlan
    },
    [mutators.SET_SELECTED_OBSERVATION] (state, selectedObservation) {
      state.selectedObservation = selectedObservation
    },
    [mutators.SET_TOKEN] (state, token) {
      state.token = token
    },
    async [mutators.ADD_PLAN_TO_TREATMENT_PLAN] (state, patient) {
      state.treatmentPlans = await getTreatmentPlan(patient)
    },
    async [mutators.SET_OBSERVATIONS] (state, treatmentPlan) {
      state.observations = await getObservation(treatmentPlan)
    }
  },
  getters: {
  },
  actions: {
  }
})
