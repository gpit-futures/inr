<template>
  <v-dialog v-model="show" persistent max-width="520px">
    <v-card>
      <v-card-text>
        <v-form ref="form" lazy-validation>
          <v-layout row align-baseline>
            <v-flex xs5>
              <v-menu lazy
                 :close-on-content-click="false"
                 transition="scale-transition"
                 v-model="testDateVisible"
                 attach>
                 <v-text-field slot="activator"
                   v-model="testDate"
                   label="Test Date"
                   readonly>
                 </v-text-field>
                 <v-date-picker :value="testDateFmt" @input="selectedDate" locale="en-GB" scrollable attach>
                 </v-date-picker>
              </v-menu>
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs5>
              <v-select
                :items="inrValues"
                v-model="inrValue"
                label="New INR"
                :rules="inrRules"
              ></v-select>
            </v-flex>
          </v-layout>
          <v-layout row>
            <v-flex xs5>
              <v-select
                :items="testingMethods"
                v-model="testingMethod"
                label="Testing method"
                :rules="testingRules"
              ></v-select>
            </v-flex>
          </v-layout>

          <v-text-field
            name="input-1"
            label="Comments"
            textarea
            v-model="comments"
          ></v-text-field>
        </v-form>
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
    return {
      show: this.showModal,
      testDate: null,
      testDateFmt: null,
      testDateVisible: false,
      testingMethod: null,
      testingMethods: ['PoCT', 'Lab'],
      comments: null,
      inrValue: null,
      inrValues: [1.9, 2.0, 2.1, 2.2, 2.3, 2.4],
      inrRules: [v => !!v || 'Please select the INR'],
      testingRules: [v => !!v || 'Please select the testing method']
    }
  },
  methods: {
    submit () {
      if (this.$refs.form.validate()) {
        let reviewDays = 14
        let nextTestDate = moment(this.testDate, 'DD-MMM-YYYY').add(reviewDays, 'days').format('DD-MMM-YYYY')
        let record = {testDate: this.testDate, inr: this.inrValue, dose: '5', reviewDays: 14, nextTestDate}
        this.$emit('submit', record)
      }
    },
    close () {
      this.$emit('close')
    },
    selectedDate (value) {
      this.testDateFmt = value
      this.testDate = moment(value, 'YYYY-MM-DD').format('DD-MMM-YYYY')
      this.testDateVisible = false
    }
  },
  watch: {
    showModal (value) {
      this.show = value
      if (value) {
        this.$refs.form.reset()
        let now = moment()
        this.testDate = now.format('DD-MMM-YYYY')
        this.testDateFmt = now.format('YYYY-MM-DD')
        this.testingMethod = null
        this.inrValue = null
        this.comments = null
      }
    }
  }
}
</script>
