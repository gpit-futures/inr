<template>
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
    <v-layout v-if="patient" row class="purple darken-1 white--text pl-2 pr-2 pt-3 pb-3 justify-space-between">
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
    <v-layout v-if="patient" row class="pa-2" justify-space-between>
      <div>
        <span class="caption">Address:</span>
        <span class="body-1">{{address}}</span>
      </div>
      <template v-if="treatmentPlan">
        <div>
          <span class="caption">Diagnosis:</span>
          <span class="body-1">{{treatmentPlan.diagnosis}}</span>
        </div>
        <div>
          <span class="caption">Drug:</span>
          <span class="body-1">{{treatmentPlan.drug}}</span>
        </div>
        <div>
          <span class="caption">Target INR:</span>
          <span class="body-1">{{treatmentPlan.targetINR}}</span>
        </div>
        <div>
          <span class="caption">Date:</span>
          <span class="body-1">Indefinite</span>
        </div>
      </template>
    </v-layout>
  </v-card>
</template>

<script>
import moment from 'moment-es6'
import { mapState } from 'vuex'

export default {
  name: 'patient-banner',
  data () {
    return {
      now: moment().format('DD-MMM-YYYY')
    }
  },
  computed: {
    ...mapState({
      patient: state => state.patient,
      treatmentPlan: state => state.treatmentPlan
    }),
    patientAge () {
      let dob = moment(this.patient.birthDate, 'DD-MMM-YYYY')
      return moment().diff(dob, 'years')
    },
    address () {
      let lines = this.patient.address.line.join(',')
      return `${lines}, ${this.patient.address.city}, ${this.patient.address.postalCode}`
    }
  }
}
</script>

<style>

</style>
