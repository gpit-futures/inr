<template>
  <v-app>
    <v-content>
      <v-container>
        <patient-banner />
        <new-patient v-if="patientNotExist"/>
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
import { DwClientConnector } from 'dw-client-connector'

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
      patientContext: state => state.patientContext,
      treatmentPlan: state => state.treatmentPlan
    }),
    patientExists () {
      return (this.patient !== null)
    },
    patientNotExist () { // No patient but a patientcontext
      return (this.patient === null) && (this.patientContext !== null)
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
    let patient = {'id': '267e175a-57fe-4b8a-a672-15012d83ed9e',
      'title': 'Mr',
      'firstName': 'Ivor',
      'lastName': 'Cox',
      'gender': 'Male',
      'phone': '(011981) 32362',
      'pasNumber': '352541',
      'nhsNumber': '9999999000',
      'dateOfBirth': '1944-07-05T23:00:00Z',
      'address': {
        'line1': '6948 Et St.',
        'line2': 'Halesowen',
        'line3': 'Worcestershire',
        'line4': null,
        'postcode': 'VX27 5DV'
      },
      'gp': {
        'name': 'Goff Carolyn D.',
        'address': {
          'line1': 'Hamilton Practice',
          'line2': '5544 Ante Street',
          'line3': 'Hamilton',
          'line4': 'Lanarkshire',
          'postcode': 'N06 5LP'
        }
      }
    }

    let treatmentPlan = {
      planDate: '14-Apr-2017',
      diagnosis: 'Atrial fibrillation',
      drug: 'Warfarin',
      targetINR: '2.0',
      dosingMethod: 'Manual Dosing',
      testingMethod: 'PoCT',
      items: []
    }

    this.$store.commit(mutators.SET_PATIENT_CONTEXT, patient)
    if (mode > 1) {
      this.$store.commit(mutators.SET_PATIENT, patient)
    }
    if (mode > 2) {
      this.$store.commit(mutators.SET_TREATMENT_PLAN, treatmentPlan)
    }

    DwClientConnector.subscribe('patient-context:changed', (data) => {
      // set patient here
    })

    DwClientConnector.subscribe('patient-context:ended', () => {
      // reset patient here and navigate to
    })
  }
}
</script>

<style>

</style>
