<template>
  <v-container>
    <v-card class="mb-4">
      <v-layout row align-baseline class="pa-2">
         <v-flex xs4>
         </v-flex>
         <v-flex xs4 class="headline text-xs-center">
           INR Module
         </v-flex>
         <v-flex xs4>
           <v-layout row justify-space-between>
             <b>Dr E. Taylor</b>
             <span>{{now}}</span>
           </v-layout>
         </v-flex>
      </v-layout>
      <v-layout row class="purple darken-1 white--text pl-2 pr-2 pt-3 pb-3 justify-space-between">
        <span class="body-1">{{patient.name.text}}</span>
        <div>
          <span class="caption">Born:</span>
          <span class="body-1">{{patient.birthDate}} ({{patientAge}}y)</span>
        </div>
        <div>
          <span class="caption">Gender:</span>
          <span class="body-1">{{patient.gender}}</span>
        </div>
        <div>
          <span class="caption">NHS Number:</span>
          <span class="body-1">{{patient.identifier}}</span>
        </div>
        <div>
          <span class="caption">GP Practice:</span>
          <span class="body-1">{{patient.generalPractitioner.identifier}}</span>
        </div>
      </v-layout>
      <v-layout row class="pa-2" justify-space-between>
        <div>
          <span class="caption">Address:</span>
          <span class="body-1">{{patient.address.text}}</span>
        </div>
        <div>
          <span class="caption">Diagnosis:</span>
          <span class="body-1">Atrial fibrillation</span>
        </div>
        <div>
          <span class="caption">Drug:</span>
          <span class="body-1">Warfarin</span>
        </div>
        <div>
          <span class="caption">Target INR:</span>
          <span class="body-1">2.2</span>
        </div>
        <div>
          <span class="caption">Date:</span>
          <span class="body-1">Indefinite</span>
        </div>
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
      <v-tab-item key="manage">
        <div class="headline mt-3">
          Manage...
        </div>
      </v-tab-item>
      <v-tab-item key="options">
        <div class="headline mt-3">
          Options...
        </div>
      </v-tab-item>
      <v-tab-item key="help">
        <div class="headline mt-3">
          Help...
        </div>
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
import TreatmentPlans from './TreatmentPlans'
import Demographics from './Demographics'
import moment from 'moment-es6'

export default {
  name: 'Page',
  components: {TreatmentPlans, Demographics},
  data () {
    return { // Some mock data to fill the page
      activeTab: null,
      now: moment().format('DD-MMM-YYYY'),
      patient: {
        name: {
          text: 'SMITH, John (Mr)'
        },
        birthDate: '01-Aug-1975',
        gender: 'Male',
        identifier: '111 999 0001',
        generalPractitioner: {
          identifier: 'AB0011'
        },
        address: {
          text: '1 Kings Road, Leeds, LS11 5AA'
        }
      }
    }
  },
  computed: {
    patientAge () {
      let dob = moment(this.patient.birthDate, 'DD-MMM-YYYY')
      return moment().diff(dob, 'years')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
