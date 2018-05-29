import Vue from 'vue'
import Vuex from 'vuex'
import mutators from './mutators'
import router from '../router'
import { getPatient } from '../api/patient'
import { getTreatmentPlan } from '../api/treatmentPlan'
import { ukDateToInternational } from '../utilities'
import { stat } from 'fs';
import { getObservation } from '../api/observation';

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    patient: null,
    patientContext: null,
    treatmentPlans: [],
    selectedPlan: null,
    observations: [],
    static: {
      inrValues: ['1.8', '1.9', '2.0', '2.1', '2.2', '2.3', '2.4', '99']
    }
  },
  mutations: {
    /* The patient stored in the INR system - get the patient from the INR middleware - if none exists promt user.
    axios.get('../patient/' + patient.nhsNumber) */
    [mutators.SET_PATIENT] (state, patient) {
      state.patient = patient
    },
    async [mutators.SET_PATIENT_INR] (state, patient) {
      state.patient = await getPatient(patient)
      state.treatmentPlans = await getTreatmentPlan(patient)
      state.selectedPlan = state.treatmentPlans[0]
      if (state.patient === null) {
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
    [mutators.ADD_PLAN_TO_TREATMENT_PLAN] (state, plan) {
      state.treatmentPlans = [plan]
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
