<template>
  <v-app>
    <v-content>
      <v-container fill-height>
        <v-layout column>
        <patient-banner />
        <router-view></router-view>
      </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import PatientBanner from './components/PatientBanner'
import mutators from './store/mutators'
import { DwClientConnector } from 'dw-client-connector'

export default {
  name: 'App',
  components: {
    PatientBanner
  },
  mounted () {
    console.log('------initialised INR')

    DwClientConnector.subscribe('patient-context:changed', (patient) => {
      console.log('INR patient-context:changed', patient)
      this.$store.commit(mutators.SET_PATIENT_CONTEXT, patient)
      /* Note: the patient should be loaded from INR backend, if the patient is not there then
         the NewPatient page should be displayed. */
      this.$store.commit(mutators.SET_PATIENT, patient)
      this.$router.push({name: 'NewPlan'})
    })

    DwClientConnector.subscribe('patient-context:ended', () => {
      console.log('INR patient-context:ended')
      this.$store.commit(mutators.SET_PATIENT_CONTEXT, null)
      this.$store.commit(mutators.SET_PATIENT, null)
      this.$router.push({name: 'LandingPage'})
    })
  }
}
</script>
