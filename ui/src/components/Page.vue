<template>
  <v-container>
    <v-card class="mb-4">
      <v-layout row align-baseline class="pa-2">
         <v-flex xs4>
         </v-flex>
         <v-flex xs3 class="display-1 text-xs-center">
           INR Module
         </v-flex>
         <v-flex xs3>
           <b>Dr E. Taylor</b>
         </v-flex>
         <v-flex xs2 class="text-xs-right">
           26-April-2018
         </v-flex>
      </v-layout>
      <v-layout row class="purple darken-1 white--text pl-2 pr-2 pt-3 pb-3">
        <v-flex xs3>
          <span class="body-1">SMITH, John (Mr)</span>
        </v-flex>
        <v-flex xs3>
          <span class="caption">Born:</span>
          <span class="body-1">01-Aug-1975 (43y)</span>
        </v-flex>
        <v-flex xs2>
          <span class="caption">Gender:</span>
          <span class="body-1">Male</span>
        </v-flex>
        <v-flex xs2>
          <span class="caption">NHS Number:</span>
          <span class="body-1">111 999 0000</span>
        </v-flex>
        <v-flex xs2>
          <span class="caption">GP Practice:</span>
          <span class="body-1">AB0011</span>
        </v-flex>
      </v-layout>
      <v-layout row class="pa-2">
        <v-flex xs3>
          <span class="caption">Address:</span>
          <span class="body-1">1 Kings Road, Leeds, LS11 5AA</span>
        </v-flex>
        <v-flex xs3>
          <span class="caption">Diagnosis:</span>
          <span class="body-1">Atrial fibrillation</span>
        </v-flex>
        <v-flex xs2>
          <span class="caption">Drug:</span>
          <span class="body-1">Warfarin</span>
        </v-flex>
        <v-flex xs2>
          <span class="caption">Target INR:</span>
          <span class="body-1">2.2</span>
        </v-flex>
        <v-flex xs2>
          <span class="caption">Date:</span>
          <span class="body-1">Indefinite</span>
        </v-flex>
      </v-layout>
    </v-card>

    <v-tabs color="purple lighten-5" slider-color="purple darken-4" grow v-model="activeTab">
      <v-tab key="plans">Treatment Plans</v-tab>
      <v-tab key="demo">Demographics</v-tab>
      <v-tab key="manage">Patient Management</v-tab>
      <v-tab key="options">Options</v-tab>
      <v-tab key="help">Help</v-tab>
      <v-tab-item key="plans">
        <treatment-plans />
      </v-tab-item>
      <v-tab-item key="demo">
        <demographics />
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
import TreatmentPlans from './TreatmentPlans'
import Demographics from './Demographics'

export default {
  name: 'Page',
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
