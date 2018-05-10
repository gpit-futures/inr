<template>
  <v-app>
    <v-content>
      <Page v-if="isPage" />
      <new-patient v-if="isNewPatient" />
      <new-treatment-plan v-if="isNewPlan" />
    </v-content>
  </v-app>
</template>

<script>
import Page from './components/Page'
import NewPatient from './components/NewPatient'
import NewTreatmentPlan from './components/NewTreatmentPlan'

export default {
  name: 'App',
  components: {
    Page, NewPatient, NewTreatmentPlan
  },
  computed: {
    // temp hack for different modes, until backend/wrapper available.
    isPage () {
      return window.location.pathname.length === 1
    },
    isNewPatient () {
      console.log(window.location.pathname)
      return window.location.pathname.length === 2
    },
    isNewPlan () {
      return window.location.pathname.length === 3
    }
  }
}
</script>

<style>

</style>
