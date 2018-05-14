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

    <treatment-plan-table v-if="!addingHistorical" :plan="treatmentPlan" :test="test" :planSuggested="planSuggested"
        @save="savePlan" @cancel="cancelPlan" />

    <historical v-if="addingHistorical" :plan="treatmentPlan" @add-historical="addHistoricalRecord" @cancel-historical="cancelHistorical"/>

    <v-layout v-if="showButtons" row class="mt-3">
      <v-btn color="primary" @click="addINR">Add New INR <v-icon right>playlist_add</v-icon></v-btn>
      <v-btn color="primary" @click="addHistorical">Add Historical <v-icon right>history</v-icon></v-btn>
    </v-layout>

    <inr-dialog :show-modal="addingInr" @close="addingInr = false" @submit="addedINR" />
  </div>
</template>

<script>
import InrDialog from './InrDialog'
import TreatmentPlanTable from './TreatmentPlanTable'
import Historical from './Historical'
import { mapState } from 'vuex'
import mutators from '../store/mutators'

export default {
  name: 'treatment-plans',
  components: {InrDialog, TreatmentPlanTable, Historical},
  methods: {
    addINR () {
      this.addingInr = true
    },
    addHistorical () {
      this.addingHistorical = true
    },
    addHistoricalRecord (record) {
      record.id = ++this.lastId
      this.$store.commit(mutators.ADD_TEST_TO_PLAN, record)
      this.addingHistorical = false
    },
    cancelHistorical () {
      this.addingHistorical = false
    },
    addedINR (plan) {
      console.log(plan)
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
      this.$store.commit(mutators.ADD_TEST_TO_PLAN, this.test)
    }
  },
  data () {
    return {
      addingInr: false,
      addingHistorical: false,
      lastId: 2,
      test: null,
      planSuggested: false,
      planDates: ['01-May-2017'],
      selectedPlanDate: '01-May-2017'
    }
  },
  computed: {
    ...mapState({
      treatmentPlan: state => state.treatmentPlan
    }),
    showButtons () {
      return (!this.planSuggested) && (!this.addingHistorical)
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

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
