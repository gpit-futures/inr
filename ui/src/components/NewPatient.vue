<template>
  <v-container>
    <v-tabs color="purple lighten-5" slider-color="purple darken-4" grow v-model="activeTab">
      <v-tab key="add-patient">Add Patient</v-tab>
      <v-tab key="test-due">Test Due</v-tab>
      <v-tab key="results">Results</v-tab>
      <v-tab-item key="add-patient">
        <v-form ref="form" v-if="patientContext" lazy-validation>
          <v-layout row class="mt-3">
            <v-flex xs6 class="pr-3">
              <div class="title">Demographics</div>
              <v-text-field v-model="identifier" label="Patient Number" :rules="requiredRules"/>
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
                label="Ethnicity"
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
              <v-text-field v-model="contactAddress2" label="Address Line 2" />
              <v-text-field v-model="contactAddress3" label="Address Line 3" />
              <v-text-field v-model="contactTown" label="Town / City" :rules="requiredRules" />
              <v-select
                :items="counties"
                v-model="contactCounty"
                label="County"
                :rules="requiredRules"></v-select>
              <v-text-field v-model="contactPostcode" label="Postcode" :rules="requiredRules" />
              <v-text-field v-model="contactTelephone" label="Home Tel" />
              <v-text-field v-model="contactMobile" label="Mobile" />
              <v-text-field v-model="contactEmail" label="Email" />
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
import { mapState } from 'vuex'
import mutators from '../store/mutators'

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
      identifier: '123 456 789',
      nhsNumber: '78901234',
      patientTitle: 'Miss',
      patientLastName: 'Smith',
      patientFirstName: 'Jane',
      patientDOB: '01-Jan-1980',
      patientDOBVisible: false,
      patientSex: 'Female',
      patientGender: 'Female',
      patientEthicity: 'White',
      patientLanguage: 'English',
      patientMaritalStatus: 'Divorced',
      contactAddress1: '4 Tile Terrace',
      contactAddress2: null,
      contactAddress3: null,
      contactTown: 'Brighouse',
      contactCounty: 'West Yorkshire',
      contactPostcode: 'Hd9 9FX',
      contactTelephone: null,
      contactMobile: null,
      contactEmail: 'jane23.smith45@gmail.com',
      requiredRules: [v => !!v || 'Required value']
    }
  },
  computed: {
    ...mapState({
      patientContext: state => state.patientContext
    })
  },
  methods: {
    selectedDOB (value) {
      this.patientDOB = moment(value, 'YYYY-MM-DD').format('DD-MMM-YYYY')
      this.patientDOBVisible = false
    },
    save () {
      if (this.$refs.form.validate()) {
        console.log('save')
        let lastName = this.patientLastName.toUpperCase()
        let addressLines = []
        if (this.contactAddress1) {
          addressLines.push(this.contactAddress1)
        }
        if (this.contactAddress2) {
          addressLines.push(this.contactAddress2)
        }
        if (this.contactAddress3) {
          addressLines.push(this.contactAddress3)
        }
        let patient = {
          name: {
            text: `${lastName}, ${this.patientFirstName} (${this.patientTitle})`
          },
          birthDate: this.patientDOB,
          gender: this.patientGender,
          identifier: this.identifier,
          nhsNumber: this.nhsNumber,
          generalPractitioner: {
            identifier: 'AB0011'
          },
          address: {
            line: addressLines,
            city: this.contactTown,
            postalCode: this.contactPostcode
          }
        }
        this.$store.commit(mutators.SET_PATIENT, patient)
      }
    }
  }
}
</script>
