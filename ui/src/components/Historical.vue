<template>
  <v-form ref="historicalForm" lazy-validation>
    <v-layout row mb-1>
      <span class="title">Historical Treatment</span>
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
           <v-date-picker :value="dateFmt" @input="selectedDate" locale="en-GB" scrollable attach>
           </v-date-picker>
        </v-menu>
      </v-flex>
      <v-flex xs2 pa-2>
        <v-select
          :items="inrValues"
          v-model="inrValue"
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
          :rules="requiredSelect"
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
import moment from 'moment-es6'
import { internationalDateToUk } from '../utilities'

let makeDays = function (max) {
  let result = [{text: 'Day 1', value: 1}]
  for (let i = 2; i < max; i++) {
    result.push({text: `Days ${i}`, value: i})
  }
  return result
}

export default {
  name: 'historical',
  props: ['plan'],
  data () {
    return {
      dateVisible: false,
      date: null,
      dose: null,
      dateFmt: null,
      inrValue: null,
      omit: null,
      reviewPeriod: null,
      targetInr: this.plan.targetINR,
      inrValues: ['1.8', '1.9', '2.0', '2.1', '2.2', '2.3', '2.4'],
      doses: [1.0, 2.0, 3.0, 4.0, 5.0, 6.0],
      omits: ['Not Known'].concat(makeDays(6)),
      reviewPeriods: makeDays(30),
      requiredSelect: [v => !!v || 'Please select a value'],
      requiredDate: [v => !!v || 'Please pick a date']
    }
  },
  methods: {
    selectedDate (value) {
      this.dateFmt = value
      this.date = internationalDateToUk(value)
      this.dateVisible = false
    },
    save () {
      if (this.$refs.historicalForm.validate()) {
        let nextTestDate = moment(this.date, 'DD-MMM-YYYY').add(this.reviewPeriod, 'days').format('DD-MMM-YYYY')
        let historical = {
          testDate: this.date, inr: this.inrValue, dose: this.dose, reviewDays: this.reviewPeriod, nextTestDate
        }
        this.$emit('add-historical', historical)
      }
    },
    cancel () {
      this.$emit('cancel-historical')
    }
  }
}
</script>

<style>

</style>
