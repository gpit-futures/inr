<template>
  <v-app>
    <v-content>
      <v-container>
        <patient-banner />

        <new-patient v-if="!patientExists" />
        <page v-if="patientWithPlan" />
        <new-treatment-plan v-if="patientWithNoPlan" />
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import PatientBanner from './components/PatientBanner'
import Page from './components/Page'
import NewPatient from './components/NewPatient'
import NewTreatmentPlan from './components/NewTreatmentPlan'
import mutators from './store/mutators'
import { mapState } from 'vuex'

export default {
  name: 'App',
  components: {
    Page, NewPatient, NewTreatmentPlan, PatientBanner
  },
  data () {
    return {

    }
  },
  computed: {
    ...mapState({
      patient: state => state.patient,
      treatmentPlan: state => state.treatmentPlan
    }),
    patientExists () {
      return (this.patient !== null)
    },
    patientWithNoPlan () {
      return (this.patient !== null) && (this.treatmentPlan == null)
    },
    patientWithPlan () {
      return (this.patient !== null) && (this.treatmentPlan !== null)
    }
  },
  mounted () {
    /* For development reasons the length of the url will determine whether
     - the patient has been added to interval
     - the plan has been created
     - tests have been added to the plan
    */

    let mode = window.location.pathname.length
    let patient = {
      name: {
        text: 'SMITH, John (Mr)'
      },
      birthDate: '01-Aug-1975',
      gender: 'Male',
      identifier: '111 999 0001',
      nhsNumber: '123456',
      generalPractitioner: {
        identifier: 'AB0011'
      },
      address: {
        line: ['1 Kings Road', 'Hunslet'],
        city: 'Leeds',
        postalCode: 'LS11 5AA'
      }
    }

    let treatmentPlan = {
      planDate: '14-Apr-2017',
      diagnosis: 'Atrial fibrillation',
      drug: 'Warfarin',
      targetINR: '2.0',
      dosingMethod: 'Manual Dosing',
      testingMethod: 'PoCT',
      items: [ /*
        {id: 2, testDate: '01-May-2017', inr: '2.2', dose: '2.4', reviewDays: 21, nextTestDate: '14-May-2017'},
        {id: 1, testDate: '14-April-2017', inr: '2.2', dose: '2.3', reviewDays: 14, nextTestDate: '01-May-2017'} */ ]
    }

    this.$store.commit(mutators.SET_PATIENT_CONTEXT, patient)
    if (mode > 1) {
      this.$store.commit(mutators.SET_PATIENT, patient)
    }
    if (mode > 2) {
      this.$store.commit(mutators.SET_TREATMENT_PLAN, treatmentPlan)
    }
  }
}
</script>

<style>

</style>
