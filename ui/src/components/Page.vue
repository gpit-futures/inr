<template>
    <v-tabs color="purple lighten-5" slider-color="purple darken-4" grow v-model="activeTab">
      <v-tab key="plans">Treatment Plans</v-tab>
      <v-tab key="demo">Demographics</v-tab>
      <v-tab key="manage">Patient Management</v-tab>
      <v-tab key="notes">Notes</v-tab>
      <v-tab key="summary">Summary</v-tab>
      <v-tab-item key="plans">
        <treatment-plans />
      </v-tab-item>
      <v-tab-item key="demo">
        <demographics />
      </v-tab-item>
      <v-tab-item key="manage">
        <div class="headline mt-3">
          Manage...
        </div>
      </v-tab-item>
      <v-tab-item key="notes">
        <div class="headline mt-3">
          Notes...
        </div>
      </v-tab-item>
      <v-tab-item key="summary">
        <div class="headline mt-3">
          Summary...
        </div>
      </v-tab-item>
    </v-tabs>
</template>

<script>
import TreatmentPlans from './TreatmentPlans'
import Demographics from './Demographics'

export default {
  name: 'page',
  components: {TreatmentPlans, Demographics},
  data () {
    return { // Some mock data to fill the page
      activeTab: null
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
