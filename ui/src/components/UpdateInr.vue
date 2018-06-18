<template>
  <v-form ref="historicalForm" lazy-validation>
    <v-layout row mb-1>
      <span class="title">Update Inr</span><br>
      {{selectedObservation}}<br>
      date: {{date}}<br>
      Inr Value: {{selectedObservation.valueQuantity.value}}<br>
      dose: {{dose}}<br>
      review period: {{reviewPeriod}}<br>
      target Inr: {{targetInr}}
    </v-layout>

    <v-layout row class="purple darken-1 white--text text-xs-center">
      <v-flex xs2 class="py-1">Date</v-flex>
      <v-flex xs2 class="py-1">INR</v-flex>
      <v-flex xs2 class="py-1">Dose (mg/day)</v-flex>
      <v-flex xs2 class="py-1">Omits</v-flex>
      <v-flex xs2 class="py-1">Review (days)</v-flex>
      <v-flex xs2 class="py-1">Target INR</v-flex>
    </v-layout>
    <v-layout row>
      <v-flex xs2 pa-2>
        <v-menu lazy
           :close-on-content-click="false"
           transition="scale-transition"
           v-model="dateVisible"
           attach>
           <v-text-field slot="activator"
             v-model="date"
             label="Select Date"
             :rules="requiredDate"
             readonly>
           </v-text-field>
           <v-date-picker :value="dateFmt" :max="todaysDate" @input="selectedDate" locale="en-GB" scrollable attach>
           </v-date-picker>
        </v-menu>
      </v-flex>
      <v-flex xs2 pa-2>
        <v-select
          :items="inrValues"
          v-model="selectedObservation.valueQuantity.value"
          label="Select INR"
          :rules="requiredSelect"
        ></v-select>
      </v-flex>
      <v-flex xs2 pa-2>
        <v-select
          :items="doses"
          v-model="dose"
          label="Select Dose"
          :rules="requiredSelect"
        ></v-select>
      </v-flex>
      <v-flex xs2 pa-2>
        <v-select
          :items="omits"
          v-model="omit"
          item-text='text' item-value="value"
          label="Select Omits"
          :rules="requiredSelect"
        ></v-select>
      </v-flex>
      <v-flex xs2 pa-2>
        <v-select
          :items="reviewPeriods"
          v-model="reviewPeriod"
          item-text='text' item-value="value"
          label="Select Review Period"
          :rules="requiredSelect"
        ></v-select>
      </v-flex>
      <v-flex xs2 pa-2>
        <v-select
          :items="inrValues"
          v-model="targetInr"
          :rules="requiredSelect" disabled
        ></v-select>
      </v-flex>
    </v-layout>
    <v-layout row justify-end class="mt-3"  style="margin-bottom: 260px">
      <v-btn color="primary" @click="save">Save</v-btn>
      <v-btn color="primary" @click="cancel">Cancel</v-btn>
    </v-layout>
  </v-form>
</template>

<script>
import { mapState } from 'vuex'
import { internationalDateToUk, ukDateAddDays } from '../utilities'
import moment from 'moment-es6'
import mutators from '../store/mutators'

let makeDays = function (max) {
  let result = [{text: 'Day 1', value: 1}]
  for (let i = 2; i < max; i++) {
    result.push({text: `Days ${i}`, value: i})
  }
  return result
}

let strip = function (value, number) {
  return value.replace(/<[^>]+>/ig, '').split(',')[number]
}

export default {
  name: 'update-inr',
  props: ['plan'],
  data () {
    return {
      dateVisible: false,
      dateFmt: null,
      omit: 1,
      doses: [1.0, 2.0, 3.0, 4.0, 5.0, 6.0],
      omits: ['Not Known'].concat(makeDays(6)),
      reviewPeriods: makeDays(30),
      requiredSelect: [v => !!v || 'Please select a value'],
      requiredDate: [v => !!v || 'Please pick a date'],
      todaysDate: moment().format('YYYY-MM-DD'),
      selectedObservation: this.$store.state.selectedObservation,
      date: internationalDateToUk(this.$store.state.selectedObservation.effectiveDateTime),
      inrValue: this.$store.state.selectedObservation.valueQuantity.value,
      dose: Number(strip(this.$store.state.selectedObservation.text.div, 0)),
      reviewPeriod: Number(strip(this.$store.state.selectedObservation.text.div, 1)),
      targetInr: Number(strip(this.$store.state.selectedPlan.text.div, 0))

    }
  },
  computed: {
    ...mapState({
      inrValues: state => state.static.inrValues
    })
  },
  methods: {
    selectedDate (value) {
      this.dateFmt = value
      this.date = internationalDateToUk(value)
      this.selectedObservation.effectiveDateTime = value
      this.dateVisible = false
    },
    save () {
      if (this.$refs.historicalForm.validate()) {
        let nextTestDate = ukDateAddDays(this.date, this.reviewPeriod)
        this.selectedObservation.text.div = this.dose + ',' + this.reviewPeriod + ',' + nextTestDate
        this.selectedObservation.effectiveDateTime = this.selectedObservation.effectiveDateTime
        this.$store.commit(mutators.SET_SELECTED_OBSERVATION, this.selectedObservation)
        this.$emit('update-historical', this.selectedObservation)
      }
    },
    cancel () {
      this.$emit('cancel-update')
    }
  }
}
</script>

<style>

</style>
