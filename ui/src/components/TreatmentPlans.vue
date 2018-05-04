<template>
  <div>
    <v-card flat class="mt-3 px-3 pb-3">
      <v-layout row align-baseline>
        <span class="caption">Treatment Plan:</span>
        <v-select
            :items="planDates"
            v-model="selectedPlanDate"
            label="Select"
            single-line
            hide-details
            class="date-select d-inline-block ml-1"
          ></v-select>
      </v-layout>
    </v-card>

    <v-card class="mb-3">
      <v-layout row class="purple darken-1 white--text text-xs-center">
        <v-flex xs2 class="py-1">Test Date</v-flex>
        <v-flex xs2 class="py-1">INR</v-flex>
        <v-flex xs3 class="py-1">Dose (mg/day)</v-flex>
        <v-flex xs3 class="py-1">Review days</v-flex>
        <v-flex xs2 class="py-1">Next Test Date</v-flex>
      </v-layout>
      <transition-group name="tests">
        <v-layout row v-for="row in items" :key="row.id" class="date-row text-xs-center">
          <v-flex xs2 class="py-1">{{row.testDate}}</v-flex>
          <v-flex xs2 class="py-1">{{row.inr}}</v-flex>
          <v-flex xs3 class="py-1">{{row.dose}}</v-flex>
          <v-flex xs3 class="py-1">{{row.reviewDays}}</v-flex>
          <v-flex xs2 class="py-1">{{row.nextTestDate}}</v-flex>
        </v-layout>
      </transition-group>
      <v-layout row v-if="planSuggested">
        <div class="body-2 pt-2 pl-2">Suggested Treatment and Schedule Plan:</div>
      </v-layout>
      <v-layout row v-if="planSuggested" class="date-row text-xs-center">
        <v-flex xs2 class="py-1">{{test.testDate}}</v-flex>
        <v-flex xs2 class="py-1">{{test.inr}}</v-flex>
        <v-flex xs3 class="py-1">{{test.dose}}</v-flex>
        <v-flex xs3 class="py-1">{{test.reviewDays}}</v-flex>
        <v-flex xs2 class="py-1">{{test.nextTestDate}}</v-flex>
      </v-layout>
    </v-card>

    <suggested-plan v-if="planSuggested" />
    <v-layout row justify-space-between class="mt-3">
      <v-btn v-if="!planSuggested" color="primary" @click="addINR">Add New INR <v-icon right>playlist_add</v-icon></v-btn>
      <v-layout v-if="planSuggested" row justify-end>
        <v-btn color="primary" @click="savePlan">Save<v-icon right>save</v-icon></v-btn>
        <v-btn>Refer</v-btn>
        <v-btn>Override</v-btn>
        <v-btn>Edit</v-btn>
        <v-btn @click="cancelPlan">Cancel</v-btn>
      </v-layout>
    </v-layout>
    <inr-dialog :show-modal="addingInr" @close="addingInr = false" @submit="addedINR" />
  </div>
</template>

<script>
import InrDialog from './InrDialog'
import SuggestedPlan from './SuggestedPlan'

export default {
  name: 'treatment-plans',
  components: {InrDialog, SuggestedPlan},
  methods: {
    addINR () {
      console.log('addINR')
      this.addingInr = true
    },
    addedINR (plan) {
      this.addingInr = false
      this.planSuggested = true
      this.test = plan
    },
    cancelPlan () {
      this.planSuggested = false
    },
    savePlan () {
      this.planSuggested = false
      this.test.id = ++this.lastId
      this.items.unshift(this.test)
    }
  },
  data () {
    return { // Some mock data to fill the page
      addingInr: false,
      lastId: 2,
      test: null, // {id: 3, testDate: '04-May-2017', inr: '2.2', dose: '2.3', reviewDays: 14, nextTestDate: '01-May-2017'},
      planSuggested: false,
      planDates: ['01-May-2017', '01-Jan-2016'],
      selectedPlanDate: '01-May-2017',
      headers: [
        {text: 'Test Date', value: 'testDate'},
        {text: 'INR', value: 'inr'},
        {text: 'Dose (mg/day)', value: 'dose'},
        {text: 'Review Days', value: 'reviewDays'},
        {text: 'Next Test Date', value: 'nextTestDate'},
        {text: 'Comments', sortable: false}],
      items: [
        {id: 1, testDate: '14-April-2017', inr: '2.2', dose: '2.3', reviewDays: 14, nextTestDate: '01-May-2017'},
        {id: 2, testDate: '01-May-2017', inr: '2.2', dose: '2.4', reviewDays: 21, nextTestDate: '14-May-2017'}]
    }
  }
}
</script>

<style scoped>
.date-select {
  max-width: 160px;
}

.date-row {
  border-bottom: 1px solid lightGray;
}
.tests-move {
  transition: transform .5s ease;
}
</style>
