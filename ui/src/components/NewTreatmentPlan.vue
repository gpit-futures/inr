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

          <v-select
            :items="treatmentDurationList"
            v-model="treatmentDuration"
            label="Treatment Duration"
            @input="changedDuration"></v-select>
        </v-flex>
      </v-layout>
    </v-form>

    <v-form v-if="showWarfarin" ref="warfarinForm" lazy-validation>
      <v-layout row class="mt-3">
        <v-flex xs6>
           <div class="title mt-3 mb-2">Warfarin Details</div>
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
             <v-btn color="primary" @click="savePlan">Save<v-icon right>save</v-icon></v-btn>
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

export default {
  name: 'new-treatment-plan',
  data () {
    return {
      planStartVisible: false,
      planStartDate: null,
      diagnosisList: ['Atrial Fibrillation', 'Dilated Cardiomyopathy', 'DVT (distal, npn-surgical, no risk factors)',
        'DVT (distal, surgical, no risk factors)', 'DVT (isolated calf-vein)', 'Prosthetic Heart Valve (bioprosthetic, corrected risk factors)',
        'Prosthetic Heart Valve (bioprosthetic, mital, no risk factors)'],
      diagnosis: null,
      drugList: [],
      drug: null,
      treatmentDurationList: [],
      treatmentDuration: null,
      showWarfarin: false,
      dosingMethod: null,
      dosingMethods: ['Coventry Maintenance', 'Hilingdon Maintenance', 'Manual Dosing'],
      testingMethod: null,
      testingMethods: ['PoCT', 'Lab'],
      targetINR: null,
      requiredRules: [v => !!v || 'Required value']
    }
  },
  computed: {
    ...mapState({
      inrValues: state => state.static.inrValues
    })
  },
  methods: {
    selectedPlanStart (value) {
      this.planStartDate = internationalDateToUk(value)
      this.planStartVisible = false
    },
    changedDiagnosis () {
      this.drug = null
      this.drugList = ['Apixaban', 'Enoxaparin', 'Warfarin']
      this.treatmentDuration = null
      this.treatmentDurationList = []
    },
    changedDrug () {
      this.treatmentDuration = null
      this.treatmentDurationList = ['indefinite', '6 months']
    },
    changedDuration () {
      this.showWarfarin = true
    },
    savePlan () {
      if (this.$refs.warfarinForm.validate()) {
        let treatmentPlan = {
          planDate: this.planStartDate,
          diagnosis: this.diagnosis,
          drug: this.drug,
          targetINR: this.targetINR,
          dosingMethod: this.dosingMethod,
          testingMethod: this.testingMethod,
          treatmentDuration: this.treatmentDuration,
          items: []
        }
        this.$store.commit(mutators.SET_TREATMENT_PLAN, treatmentPlan)
        this.$router.push({name: 'Tests'})
      }
    }
  },
  mounted () {
    this.$store.commit(mutators.SET_TREATMENT_PLAN, null)
  }
}
</script>
