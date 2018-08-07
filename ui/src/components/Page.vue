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
        <div class="headline mt-3">
          Demographics...
        </div>
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

export default {
  name: 'page',
  components: { TreatmentPlans },
  data () {
    return {
      activeTab: null
    }
  }
}
</script>
