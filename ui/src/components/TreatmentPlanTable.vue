<template>
  <div>
    <v-card v-if="plan" class="mb-3">
      <v-layout row  v-if="plan.items.length > 0" class="purple darken-1 white--text text-xs-center">
        <v-flex xs2 class="py-1">Test Date</v-flex>
        <v-flex xs2 class="py-1">INR</v-flex>
        <v-flex xs3 class="py-1">Dose (mg/day)</v-flex>
        <v-flex xs3 class="py-1">Review days</v-flex>
        <v-flex xs2 class="py-1">Next Test Date</v-flex>
      </v-layout>
      <v-layout row v-if="plan.items.length === 0" class="purple darken-1 white--text text-xs-center">
        <v-flex xs12 py-1>This patient has no recorded treatments since the treatment plan's start date: {{plan.planDate}}</v-flex>
      </v-layout>
      <transition-group name="tests">
        <v-layout row v-for="row in plan.items" :key="row.id" class="date-row text-xs-center">
          <v-flex xs2 class="py-1">{{row.testDate}}</v-flex>
          <v-flex xs2 class="py-1">{{row.inr}}</v-flex>
          <v-flex xs3 class="py-1">{{row.dose}}</v-flex>
          <v-flex xs3 class="py-1">{{row.reviewDays}}</v-flex>
          <v-flex xs2 class="py-1">{{row.nextTestDate}}</v-flex>
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

export default {
  name: 'treatment-plan-table',
  props: ['plan', 'planSuggested', 'test'],
  components: {SuggestedPlan},
  methods: {
    savePlan () {
      this.$emit('save')
    },
    cancelPlan () {
      this.$emit('cancel')
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
