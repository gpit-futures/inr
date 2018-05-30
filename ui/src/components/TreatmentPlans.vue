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

    <!-- link to observations for selected treatment plan -->
    <treatment-plan-table v-if="!addingHistorical" :test="test" :planSuggested="planSuggested"
        @save="savePlan" @cancel="cancelPlan" />

    <historical v-if="addingHistorical" :plan="treatmentPlans[0]"
        @add-historical="addHistoricalRecord" @cancel-historical="cancelHistorical"/>

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
import moment from 'moment-es6'
import mutators from '../store/mutators'
import { createObservation } from '../api/observation'

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
    // change to create observation
    async addHistoricalRecord (record) {
      record.id = ++this.lastId
      // this.$store.commit(mutators.ADD_TEST_TO_PLAN, record)
      await createObservation(this.patient, this.selectedPlan, this.patientContext, record)
      this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
      this.addingHistorical = false
    },
    cancelHistorical () {
      this.addingHistorical = false
    },
    addedINR (plan) {
      this.addingInr = false
      this.planSuggested = true
      this.test = plan
    },
    cancelPlan () {
      this.planSuggested = false
    },
    // change to create observation
    async savePlan () {
      this.planSuggested = false
      this.test.id = ++this.lastId
      await createObservation(this.patient, this.selectedPlan, this.patientContext, this.test)
      this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
    },
    copyInTreatmentValues () {
      if (this.treatmentPlans) {
        this.selectedPlanDate = this.treatmentPlans[0].id

        let i
        for (i = 0; i < this.treatmentPlans.length; i++) {
          this.planDates.push(this.treatmentPlans[i].id);
        }
      }
    },
    date (value) {
      return moment(value).format('DD-MMM-YYYY')
    },
    strip (value) {
      return value.replace(/<[^>]+>/ig, '')
    }
  },
  data () {
    return {
      addingInr: false,
      addingHistorical: false,
      lastId: 2,
      test: null,
      planSuggested: false,
      planDates: [],
      selectedPlanDate: null
    }
  },
  computed: {
    ...mapState({
      treatmentPlans: state => state.treatmentPlans,
      patient: state => state.patient,
      patientContext: state => state.patientContext,
      selectedPlan: state => state.selectedPlan,
      observations: state => state.observations
    }),
    showButtons () {
      return (!this.planSuggested) && (!this.addingHistorical)
    }
  },
  mounted () {
    this.copyInTreatmentValues()
    this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
  },
  watch: {
    treatmentPlans () {
      this.copyInTreatmentValues()
    },
    selectedPlanDate () {
      this.$store.commit(mutators.SET_SELECTED_PLAN, this.treatmentPlans.find(i => i.id === this.selectedPlanDate))
      this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
    }
  }
}
</script>

<style scoped>
.date-select {
  max-width: 25%;
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
