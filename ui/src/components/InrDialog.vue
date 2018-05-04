<template>
  <v-dialog v-model="show" persistent max-width="520px">
    <v-card>
      <v-card-text>
        <v-layout row align-baseline py-1>
          <div class="caption mr-3">
            Test Date
          </div>
          <v-menu lazy
             :close-on-content-click="false"
             transition="scale-transition"
             v-model="testDateVisible"
             attach>
             <v-layout column slot="activator">
               <v-text-field style="width: 120px; flex-basis: auto;" class="ml-1"
                 v-model="testDate"
                 hide-details
                 readonly>
               </v-text-field>
             </v-layout>
             <v-date-picker :value="testDateFmt" @input="selectedDate" locale="en-GB" scrollable attach>
             </v-date-picker>
          </v-menu>
        </v-layout>
        <v-layout row py-2>
          <v-flex class="xs6">
            <v-layout row align-baseline xjustify-start>
              <div class="caption mr-3">
                Select INR
              </div>
              <v-select
                :items="inrValues"
                v-model="inrValue"
                single-line
                hide-details
                class="inr-select"
              ></v-select>
            </v-layout>
          </v-flex>
          <v-flex class="xs6">
            <v-layout row align-baseline xjustify-start>
              <div class="caption mr-3">
                Testing method:
              </div>
              <v-select
                :items="testingMethods"
                v-model="testingMethod"
                single-line
                hide-details
                class="testing-select"
              ></v-select>
            </v-layout>
          </v-flex>
        </v-layout>
        <v-text-field
          name="input-1"
          label="Comments"
          textarea
        ></v-text-field>
      </v-card-text>
      <v-card-actions>
        <v-layout row justify-end>
          <v-btn color="primary" flat @click.stop="close">Close</v-btn>
          <v-btn color="primary" flat  @click.stop="submit">Submit</v-btn>
        </v-layout>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import moment from 'moment-es6'

export default {
  name: 'inr-dialog',
  props: ['showModal'],
  data () {
    let now = moment()
    return {
      show: this.showModal,
      testDate: now.format('DD-MMM-YYYY'),
      testDateFmt: now.format('YYYY-MM-DD'),
      testDateVisible: false,
      testingMethod: 'PoCT',
      testingMethods: ['PoCT', 'Lab'],
      inrValue: 1.9,
      inrValues: [1.9, 2.0, 2.1, 2.2, 2.3, 2.4]
    }
  },
  methods: {
    submit () {
      let record = {testDate: this.testDate, inr: this.inrValue, dose: '5', reviewDays: 14, nextTestDate: '01-Aug-2018'}
      this.$emit('submit', record)
    },
    close () {
      this.$emit('close')
    },
    selectedDate (value) {
      console.log('selectedDate', value)
      this.testDateFmt = value
      this.testDate = moment(value, 'YYYY-MM-DD').format('DD-MMM-YYYY')
      this.testDateVisible = false
    }
  },
  watch: {
    showModal (value) {
      this.show = value
    }
  }
}
</script>

<style scoped>
.inr-select {
  max-width: 80px;
}
.testing-select {
  max-width: 90px;
}
</style>
