<template>
  <div>
    <v-card v-if="selectedPlan" class="mb-3">
      <v-layout row v-if="observations === null" class="purple darken-4 white--text text-xs-center">
        <v-flex xs12 py-1>This patient has no recorded treatments since the treatment plan's start date: {{selectedPlan.period.start | date}}</v-flex>
      </v-layout>
      <v-layout row v-else class="purple darken-4 white--text text-xs-center">
        <v-flex xs2 class="py-1">Test Date</v-flex>
        <v-flex xs2 class="py-1">INR</v-flex>
        <v-flex xs3 class="py-1">Dose (mg/day)</v-flex>
        <v-flex xs3 class="py-1">Review days</v-flex>
        <v-flex xs2 class="py-1">Next Test Date</v-flex>
        <v-flex xs2 class="py-1">Edit</v-flex>
      </v-layout>
      <transition-group name="tests">
        <v-layout row v-for="row in observations" :key="row.id" class="date-row text-xs-center">
          <v-flex xs2 class="py-1">{{row.effectiveDateTime | date}}</v-flex>
          <v-flex xs2 class="py-1">{{row.valueQuantity.value}}</v-flex>
          <v-flex xs3 class="py-1">{{strip(row.text.div.split(',')[0])}}</v-flex>
          <v-flex xs3 class="py-1">{{strip(row.text.div.split(',')[1])}}</v-flex>
          <v-flex xs2 class="py-1">{{strip(row.text.div.split(',')[2]) | date}}</v-flex>
          <v-flex xs2 class="py-1"><v-btn color="primary" @click="updateHistorical(row)">Edit Record <v-icon right>edit</v-icon></v-btn></v-flex>
        </v-layout>
      </transition-group>

      <transition name="fade">
        <div v-if="planSuggested">
          <v-layout row>
            <div class="body-2 pt-2 pl-2 mt-3">Suggested Treatment and Schedule Plan:</div>
          </v-layout>
          <v-layout row class="date-row text-xs-center">
            <v-flex xs2 class="py-1">{{test.testDate}}</v-flex>
            <v-flex xs2 class="py-1">{{test.inr}}</v-flex>
            <v-flex xs3 class="py-1">{{test.dose}}</v-flex>
            <v-flex xs3 class="py-1">{{test.reviewDays}}</v-flex>
            <v-flex xs2 class="py-1">{{test.nextTestDate}}</v-flex>
          </v-layout>
        </div>
      </transition>

      <transition name="fade">
        <suggested-plan v-if="planSuggested" />
      </transition>
    </v-card>

    <v-layout v-if="planSuggested" row justify-end mt-3>
      <v-btn color="primary" @click="savePlan">Save<v-icon right>save</v-icon></v-btn>
      <v-btn>Refer</v-btn>
      <v-btn>Override</v-btn>
      <v-btn>Edit</v-btn>
      <v-btn @click="cancelPlan">Cancel</v-btn>
    </v-layout>

  </div>
</template>

<script>
import SuggestedPlan from './SuggestedPlan'
import moment from 'moment-es6'
import { mapState } from 'vuex'
import mutators from '../store/mutators'

export default {
  name: 'treatment-plan-table',
  props: ['test', 'planSuggested'],
  components: {SuggestedPlan},
  methods: {
    savePlan () {
      this.$emit('save')
    },
    cancelPlan () {
      this.$emit('cancel')
    },
    updateHistorical (selectedObservation) {
      this.$store.commit(mutators.SET_SELECTED_OBSERVATION, selectedObservation)
      this.$emit('update-historical', selectedObservation)
    },
    strip (value) {
      return value.replace(/<[^>]+>/ig, '')
    }
  },
  computed: {
    ...mapState({
      observations: state => state.observations,
      selectedPlan: state => state.selectedPlan,
      selectedObservation: state => state.selectedObservation
    })
  },
  filters: {
    date (value) {
      return moment(value).format('DD-MMM-YYYY')
    }
  }
}
</script>

<style scoped>
.tests-move {
  transition: transform .5s ease;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
