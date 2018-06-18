<template>
  <div>
    <v-card flat class="mt-3 px-3 pb-3">
      <v-layout row align-baseline>
        <span class="caption">Treatment Plan:</span>
        <v-select
            :items="planIDs"
            v-model="selectedPlanID"
            label="Select"
            single-line
            hide-details
            class="date-select d-inline-block ml-1"
          ></v-select>
      </v-layout>
    </v-card>

    <!-- link to observations for selected treatment plan -->
    <treatment-plan-table v-if="!(addingHistorical || updatingHistorical)" :test="test" :planSuggested="planSuggested"
        @save="savePlan" @cancel="cancelPlan" @update-historical="updateHistorical" />

    <historical v-if="addingHistorical" :plan="selectedPlan"
        @add-historical="addHistoricalRecord" @cancel-historical="cancelHistorical"/>

    <!-- Update INR reading -->
    <update-inr v-if="updatingHistorical" :plan="selectedPlan"
        @update-historical="updateHistoricalRecord" @cancel-update="cancelUpdateHistorical"/>

    <v-layout v-if="showButtons" row class="mt-3">
      <v-btn color="primary" @click="addINR">Add New INR <v-icon right>playlist_add</v-icon></v-btn>
      <v-btn color="primary" @click="addHistorical">Add Historical <v-icon right>history</v-icon></v-btn>
    </v-layout>

    <inr-dialog :show-modal="addingInr" @close="addingInr = false" @submit="addedINR" />
  </div>
</template>

<script>
import InrDialog from './InrDialog'
import UpdateInr from './UpdateInr'
import TreatmentPlanTable from './TreatmentPlanTable'
import Historical from './Historical'
import { mapState } from 'vuex'
import moment from 'moment-es6'
import mutators from '../store/mutators'
import { createObservation, updateObservation } from '../api/observation'

export default {
  name: 'treatment-plans',
  components: {InrDialog, TreatmentPlanTable, Historical, UpdateInr},
  methods: {
    addINR () {
      this.addingInr = true
    },
    addHistorical () {
      this.addingHistorical = true
    },
    // change to create observation
    async addHistoricalRecord (record) {
      await createObservation(this.patient, this.selectedPlan, this.patientContext, record)
      this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
      this.addingHistorical = false
    },
    cancelHistorical () {
      this.addingHistorical = false
    },
    updateHistorical () {
      this.updatingHistorical = true
    },
    // change to update observation
    async updateHistoricalRecord (record) {
      await updateObservation(record)
      this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
      this.updatingHistorical = false
    },
    cancelUpdateHistorical () {
      this.updatingHistorical = false
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
      // this.test.id = ++this.lastId
      await createObservation(this.patient, this.selectedPlan, this.patientContext, this.test)
      this.$store.commit(mutators.SET_OBSERVATIONS, this.selectedPlan)
    },
    copyInTreatmentValues () {
      if (this.treatmentPlans) {
        this.selectedPlanID = this.treatmentPlans[0].id
        let i
        this.planIDs = []
        for (i = 0; i < this.treatmentPlans.length; i++) {
          this.planIDs.push(this.treatmentPlans[i].id)
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
      updatingHistorical: false,
      test: null,
      planSuggested: false,
      planIDs: [],
      selectedPlanID: null
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
    selectedPlanID () {
      this.$store.commit(mutators.SET_SELECTED_PLAN, this.treatmentPlans.find(i => i.id === this.selectedPlanID))
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
