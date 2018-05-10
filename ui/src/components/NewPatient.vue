<template>
  <v-container>
    <v-tabs color="purple lighten-5" slider-color="purple darken-4" grow v-model="activeTab">
      <v-tab key="add-patient">Add Patient</v-tab>
      <v-tab key="test-due">Test Due</v-tab>
      <v-tab key="results">Results</v-tab>
      <v-tab-item key="add-patient">
        <v-form ref="form" lazy-validation>
          <v-layout row class="mt-3">
            <v-flex xs6 class="pr-3">
              <div class="title">Demographics</div>
              <v-text-field v-model="patientNumber" label="Patient Number" :rules="requiredRules"/>
              <v-text-field v-model="nhsNumber" label="NHS Number" :rules="requiredRules"/>
              <v-select
                :items="titles"
                v-model="patientTitle"
                label="Title"
                :rules="requiredRules"></v-select>
              <v-text-field v-model="patientLastName" label="Last Name" :rules="requiredRules" />
              <v-text-field v-model="patientFirstName" label="First Name" :rules="requiredRules" />

              <v-menu lazy
                 :close-on-content-click="false"
                 transition="scale-transition"
                 v-model="patientDOBVisible"
                 attach>
                 <v-text-field slot="activator"
                   v-model="patientDOB"
                   label="Born"
                   :rules="requiredRules"
                   readonly>
                 </v-text-field>
                 <v-date-picker @input="selectedDOB" locale="en-GB" scrollable attach>
                 </v-date-picker>
              </v-menu>

              <v-select
                :items="sexes"
                v-model="patientSex"
                label="Sex"
                :rules="requiredRules"></v-select>

              <v-select
                :items="genders"
                v-model="patientGender"
                label="Gender"
                :rules="requiredRules"></v-select>
              <v-select
                :items="ethnicities"
                v-model="patientEthicity"
                label="Gender"
                :rules="requiredRules"></v-select>
              <v-select
                :items="languages"
                v-model="patientLanguage"
                label="First Language"
                :rules="requiredRules"></v-select>
              <v-select
                :items="maritalStatuses"
                v-model="patientMaritalStatus"
                label="Marital Status"
                :rules="requiredRules"></v-select>
            </v-flex>
            <v-flex xs6 class="pl-3">
              <div class="title">Contact</div>
              <v-text-field v-model="contactAddress1" label="Address Line 1" :rules="requiredRules" />
              <v-text-field v-model="contactAddress2" label="Address Line 1" :rules="requiredRules" />
              <v-text-field v-model="contactAddress3" label="Address Line 1" :rules="requiredRules" />
              <v-text-field v-model="contactTown" label="Town / City" :rules="requiredRules" />
              <v-select
                :items="counties"
                v-model="contactCounty"
                label="County"
                :rules="requiredRules"></v-select>
              <v-text-field v-model="contactPostcode" label="Postcode" :rules="requiredRules" />
              <v-text-field v-model="contactTelephone" label="Home Tel" :rules="requiredRules" />
              <v-text-field v-model="contactMobile" label="Mobile" :rules="requiredRules" />
              <v-text-field v-model="contactEmail" label="Email" :rules="requiredRules" />
            </v-flex>
          </v-layout>
        </v-form>
        <v-layout row justify-end>
          <v-btn color="primary" @click.stop="save">Save</v-btn>
        </v-layout>
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
import moment from 'moment-es6'

export default {
  name: 'new-patient',
  data () {
    return {
      activeTab: null,
      titles: ['Mr', 'Mrs', 'Miss'],
      counties: ['North Yorkshire', 'West Yorkshire', 'East Yorkshire', 'South Yorkshire'],
      sexes: ['Male', 'Female'],
      genders: ['Male', 'Female'],
      ethnicities: ['White', 'Mixed', 'Asian or Asian British', 'Black or Black British', 'Other Ethnic Group'],
      languages: ['English', 'Catalan', 'Gujarati', 'Hindi', 'Polish'],
      maritalStatuses: ['Single', 'Married', 'Divorced', 'Not Disclosed'],
      patientNumber: null,
      nhsNumber: null,
      patientTitle: null,
      patientLastName: null,
      patientFirstName: null,
      patientDOB: null,
      patientDOBVisible: false,
      patientSex: null,
      patientGender: null,
      patientEthicity: null,
      patientLanguage: null,
      patientMaritalStatus: null,
      contactAddress1: null,
      contactAddress2: null,
      contactAddress3: null,
      contactTown: null,
      contactCounty: null,
      contactPostcode: null,
      contactTelephone: null,
      contactMobile: null,
      contactEmail: null,
      requiredRules: [v => !!v || 'Required value']
    }
  },
  methods: {
    selectedDOB (value) {
      this.patientDOB = moment(value, 'YYYY-MM-DD').format('DD-MMM-YYYY')
      this.patientDOBVisible = false
    },
    save () {
      if (this.$refs.form.validate()) {
        console.log('save')
      }
    }
  }
}
</script>
