<template>
  <v-card class="mb-4">
    <v-layout row align-baseline class="pa-2">
       <v-flex xs4>
       </v-flex>
       <v-flex xs4 class="headline text-xs-center">
         INR Module
       </v-flex>
       <v-flex xs4 v-if="patientContext">
         <v-layout row justify-space-between>
           <b>{{patientContext.gp.name}}</b>
           <span>{{now}}</span>
         </v-layout>
       </v-flex>
    </v-layout>
    <transition name="reveal">
      <div v-if="patient" key="patient">
        <v-layout row class="purple darken-4 white--text pl-2 pr-2 pt-3 pb-3 justify-space-between">
          <span class="body-1">{{patientName}}</span>
          <div>
            <span class="caption">Born:</span>
            <span class="body-1">{{patient.birthDate | date}} ({{patientAge}}y)</span>
          </div>
          <div>
            <span class="caption">Gender:</span>
            <span class="body-1">{{patient.gender}}</span>
          </div>
          <div>
            <span class="caption">NHS Number:</span>
            <span class="body-1">{{patient.identifier[0].value}}</span>
          </div>
          <div>
            <span class="caption">GP Practice:</span>
            <span class="body-1">{{patientContext.gp.address.line1}}</span>
          </div>
        </v-layout>
        <v-layout row class="pa-2" justify-space-between>
          <div class="pr-1">
            <span class="caption">Address:</span>
            <span class="body-1 address">{{address}}</span>
          </div>
          <template v-if="treatmentPlans">
            <div class="px-1">
              <span class="caption">Diagnosis:</span>
              <span class="body-1">{{selectedPlan.addresses[0].display}}</span>
            </div>
            <div class="px-1">
              <span class="caption">Drug:</span>
              <span class="body-1">{{selectedPlan.activity[0].detail.productCodeableConcept.coding[0].display}}</span>
            </div>
            <div class="px-1">
              <span class="caption">Target INR:</span>
              <span class="body-1">{{selectedPlan.text.div.split(',')[0] | strip}}</span>
            </div>
            <div class="px-1">
              <span class="caption">Date:</span>
              <span class="body-1">{{selectedPlan.period.start | date}}</span>
            </div>
          </template>
        </v-layout>
      </div>
    </transition>
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
      patientContext: state => state.patientContext,
      treatmentPlans: state => state.treatmentPlans,
      selectedPlan: state => state.selectedPlan
    }),
    patientAge () {
      let dob = moment(this.patient.birthDate)
      return moment().diff(dob, 'years')
    },
    patientName () {
      let lastName = this.patient.name[0].family.toUpperCase()
      return `${lastName}, ${this.patient.name[0].given[0]}. (${this.patient.name[0].prefix[0]})`
    },
    address () {
      let addrArray = []
      let fields = ['line', 'city', 'district', 'postalCode']
      fields.forEach(field => {
        if (this.patient.address[0][field]) {
          addrArray.push(this.patient.address[0][field])
        }
      })
      return addrArray.join(', ')
    }
  },
  filters: {
    date (value) {
      return moment(value).format('DD-MMM-YYYY')
    },
    strip (value) {
      return value.replace(/<[^>]+>/ig, '')
    }
  }
}
</script>

<style scoped>
.reveal-enter-active, .reveal-leave-active {
  transition: opacity 0.3s, max-height 0.3s;
  overflow: hidden;
  max-height: 100px;
}
.reveal-enter, .reveal-leave-to {
  opacity: 0;
  max-height: 0px;
}
</style>
