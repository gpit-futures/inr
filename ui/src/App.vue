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

export default {
  name: 'App',
  components: {
    PatientBanner
  },
  mounted () {
    console.log('------initialised INR')

    if (this.isElectron()) {
      console.log('is electron app')
      window.Bridge.updatePatientContext = patient => {
        this.$store.commit(mutators.SET_PATIENT_CONTEXT, patient)
        this.$store.commit(mutators.SET_PATIENT_INR, patient)
      }
    }

    if (this.isElectron()) {
      console.log('is electron app')
      window.Bridge.updateTokenContext = token => {
        console.log('token set: ')
        console.log(token)
        this.$store.commit(mutators.SET_TOKEN, token)
        console.log('token get: ')
        console.log(this.$store.state.token)
      }
    }
  },
  methods: {
    isElectron () {
      return 'Bridge' in window
    }
  }
}
</script>
