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
                   prepend-icon="event"
                   readonly>
                 </v-text-field>
                 <v-date-picker :value="testDateFmt" @input="selectedDate" locale="en-GB" scrollable attach>
                 </v-date-picker>
              </v-menu>
              <v-menu lazy
                  ref="timeMenu"
                 :close-on-content-click="false"
                 transition="scale-transition"
                 v-model="testTimeVisible"
                 :return-value.sync="time"
                 attach>
                 <v-text-field slot="activator"
                   v-model="time"
                   label="Test Time"
                   prepend-icon="access_time"
                   readonly>
                 </v-text-field>
                 <v-time-picker v-model="time" @change="$refs.timeMenu.save(time)" scrollable attach>
                 </v-time-picker>
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
import { mapState } from 'vuex'
import { internationalDateToUk, ukDateAddDays } from '../utilities'

export default {
  name: 'inr-dialog',
  props: ['showModal'],
  data () {
    return {
      show: this.showModal,
      testDate: null,
      time: null,
      testDateFmt: null,
      testDateVisible: false,
      testTimeVisible: false,
      testingMethod: null,
      testingMethods: ['PoCT', 'Lab'],
      comments: null,
      inrValue: null,
      inrRules: [v => !!v || 'Please select the INR'],
      testingRules: [v => !!v || 'Please select the testing method']
    }
  },
  methods: {
    submit () {
      if (this.$refs.form.validate()) {
        let reviewDays = 14
        let nextTestDate = ukDateAddDays(this.testDate, reviewDays)
        let record = {testDate: this.testDate, testTime: this.time, inr: this.inrValue, dose: '5', reviewDays: 14, nextTestDate}
        // caught by addedinr() in parent.
        this.$emit('submit', record)
      }
    },
    close () {
      this.$emit('close')
    },
    selectedDate (value) {
      this.testDateFmt = value
      this.testDate = internationalDateToUk(value)
      this.testDateVisible = false
    }
  },
  computed: {
    ...mapState({
      inrValues: state => state.static.inrValues
    })
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
