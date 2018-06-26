<template>
  <div>
    <v-form ref="planForm" lazy-validation>
      <v-layout row class="mt-3">
        <v-flex xs12>
          <div class="body-1">To add a new treatment plan for this patient enter the details in the form below.</div>
        </v-flex>
      </v-layout>
      <v-layout row>
        <v-flex xs6>
          <div class="title mt-3 mb-2">Treatment Plan</div>
          <v-menu lazy
             :close-on-content-click="false"
             transition="scale-transition"
             v-model="planStartVisible"
             attach>
             <v-text-field slot="activator"
               v-model="planStartDate"
               label="Plan Start Date"
               readonly>
             </v-text-field>
             <v-date-picker @input="selectedPlanStart" locale="en-GB" scrollable attach>
             </v-date-picker>
          </v-menu>

          <v-select
            :items="diagnosisList"
            v-model="diagnosis"
            label="Diagnosis"
            @input="changedDiagnosis"></v-select>

          <v-select
            :items="drugList"
            v-model="drug"
            label="Drug"
            @input="changedDrug"></v-select>

          <v-menu lazy
             :close-on-content-click="false"
             transition="scale-transition"
             v-model="planEndVisible"
             attach>
             <v-text-field slot="activator"
               v-model="planEndDate"
               label="Plan End Date"
               readonly>
             </v-text-field>
             <v-date-picker @input="selectedPlanEnd" :min="selectedPlanEndMax" locale="en-GB" scrollable attach>
             </v-date-picker>
          </v-menu>

        </v-flex>
      </v-layout>
    </v-form>

    <v-form v-if="showDrug" ref="drugForm" lazy-validation>
      <v-layout row class="mt-3">
        <v-flex xs6>
           <div class="title mt-3 mb-2">{{this.drug}} Details</div>
           <v-select
             :items="inrValues"
             v-model="targetINR"
             label="Target INR"
             :rules="requiredRules"></v-select>
           <v-select
             :items="dosingMethods"
             v-model="dosingMethod"
             label="Dosing Method"
             :rules="requiredRules"></v-select>
           <v-select
             :items="testingMethods"
             v-model="testingMethod"
             label="Testing Method"
             :rules="requiredRules"></v-select>
           <v-layout row justify-end>
             <v-btn color="primary" @click="savePlan" :disabled="loading">Save<v-icon right>save</v-icon><v-progress-circular v-if="loading" :width="2" indeterminate></v-progress-circular></v-btn>
           </v-layout>
        </v-flex>
      </v-layout>
    </v-form>
  </div>
</template>

<script>
import mutators from '../store/mutators'
import { mapState } from 'vuex'
import { internationalDateToUk } from '../utilities'
import { createTreatmentPlan } from '../api/treatmentPlan'
import { createEncounter } from '../api/encounter'
import moment from 'moment-es6'

export default {
  name: 'new-treatment-plan',
  data () {
    return {
      planStartVisible: false,
      planStartDate: null,
      planEndVisible: false,
      planEndDate: null,
      diagnosisList: ['Atrial Fibrillation', 'Dilated Cardiomyopathy', 'DVT (distal, npn-surgical, no risk factors)',
        'DVT (distal, surgical, no risk factors)', 'DVT (isolated calf-vein)', 'Prosthetic Heart Valve (bioprosthetic, corrected risk factors)',
        'Prosthetic Heart Valve (bioprosthetic, mital, no risk factors)'],
      diagnosis: null,
      drugList: [],
      drug: null,
      treatmentDurationList: [],
      treatmentDuration: null,
      showDrug: false,
      dosingMethod: null,
      dosingMethods: ['Coventry Maintenance', 'Hilingdon Maintenance', 'Manual Dosing'],
      testingMethod: null,
      testingMethods: ['PoCT', 'Lab'],
      targetINR: null,
      requiredRules: [v => !!v || 'Required value'],
      newEncounter: {},
      plan: {},
      selectedPlanEndMax: null,
      loading: false

    }
  },
  computed: {
    ...mapState({
      inrValues: state => state.static.inrValues,
      patient: state => state.patient,
      patientContext: state => state.patientContext,
      treatmentPlans: state => state.treatmentPlans
    })
  },
  methods: {
    selectedPlanStart (value) {
      this.planStartDate = internationalDateToUk(value)
      this.planStartVisible = false
    },
    selectedPlanEnd (value) {
      this.planEndDate = internationalDateToUk(value)
      this.planEndVisible = false
    },
    changedDiagnosis () {
      this.drug = null
      this.drugList = ['Apixaban', 'Enoxaparin', 'Warfarin']
      this.treatmentDuration = null
      this.treatmentDurationList = []
    },
    changedDrug () {
      this.showDrug = true
    },
    async savePlan () {
      if (this.$refs.drugForm.validate()) {
        // create new encounter/careplan
        this.newEncounter = await createEncounter(this.patient, this.planStartDate, this.diagnosis, this.patientContext)
        this.loading = true
        await createTreatmentPlan(this.patient, this.newEncounter, this.targetINR, this.dosingMethod, this.testingMethod, this.diagnosis, this.planStartDate, this.planEndDate, this.drug, this.patientContext)
        this.loading = false
        this.$store.commit(mutators.ADD_PLAN_TO_TREATMENT_PLAN, this.patient)
        this.$router.push({name: 'Tests'})
      }
    }
  },
  mounted () {
    this.$store.commit(mutators.SET_TREATMENT_PLAN, null)
  },
  watch: {
    planStartDate (value) {
      this.selectedPlanEndMax = moment(value).format('YYYY-MM-DD')
    }
  }
}
</script>
