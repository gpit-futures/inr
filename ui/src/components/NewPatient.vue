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
              <v-text-field v-model="identifier" label="Patient Number" disabled/>
              <v-text-field v-model="nhsNumber" label="NHS Number" :rules="requiredRules" disabled/>
              <v-select
                :items="titles"
                v-model="patientTitle"
                label="Title"
                :rules="requiredRules"
                disabled></v-select>
              <v-text-field v-model="patientLastName" label="Last Name" :rules="requiredRules" disabled/>
              <v-text-field v-model="patientFirstName" label="First Name" :rules="requiredRules" disabled/>

              <v-menu lazy
                 :close-on-content-click="false"
                 transition="scale-transition"
                 v-model="patientDOBVisible"
                 attach>
                 <v-text-field slot="activator"
                   v-model="patientDOB"
                   label="Born"
                   :rules="requiredRules"
                   readonly disabled>
                 </v-text-field>
                 <v-date-picker @input="selectedDOB" :value="patientDOBpicker"  scrollable attach disabled>
                 </v-date-picker>
              </v-menu>
              <v-select
                :items="sexes"
                v-model="patientSex"
                label="Sex"
                :rules="requiredRules"
                disabled></v-select>
              <v-select
                :items="genders"
                v-model="patientGender"
                label="Gender"
                :rules="requiredRules"
                disabled></v-select>
              <v-select
                :items="ethnicities"
                v-model="patientEthicity"
                label="Ethnicity"
                :rules="requiredRules"
                disabled></v-select>
              <v-select
                :items="languages"
                v-model="patientLanguage"
                label="First Language"
                :rules="requiredRules"
                disabled></v-select>
              <v-select
                :items="maritalStatuses"
                v-model="patientMaritalStatus"
                label="Marital Status"
                :rules="requiredRules"
                disabled></v-select>
            </v-flex>
            <v-flex xs6 class="pl-3">
              <div class="title">Contact</div>
              <v-text-field v-model="contactAddress1" label="Address Line 1" :rules="requiredRules" disabled/>
              <v-text-field v-model="contactAddress2" label="Address Line 2" disabled/>
              <v-text-field v-model="contactAddress3" label="Address Line 3" disabled/>
              <v-text-field v-model="contactTown" label="Town / City" disabled/>
              <v-text-field v-model="contactPostcode" label="Postcode" :rules="requiredRules" disabled/>
              <v-text-field v-model="contactTelephone" label="Home Tel" disabled/>
              <v-text-field v-model="contactMobile" label="Mobile" disabled/>
              <v-text-field v-model="contactEmail" label="Email" disabled/>
            </v-flex>
          </v-layout>
        </v-form>
        <v-layout row justify-end>
          <v-btn color="primary" @click.stop="save">Add Patient to INR System</v-btn>
        </v-layout>
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
import moment from 'moment-es6'
import { mapState } from 'vuex'
import mutators from '../store/mutators'
import { internationalDateToUk } from '../utilities'
import { createPatient } from '../api/patient'

export default {
  name: 'new-patient',
  computed: {
    ...mapState({
      patientContext: state => state.patientContext
    })
  },
  data () {
    return {
      activeTab: null,
      titles: ['Mr', 'Mrs', 'Miss'],
      // counties: ['North Yorkshire', 'West Yorkshire', 'East Yorkshire', 'South Yorkshire'],
      sexes: ['male', 'female'],
      genders: ['male', 'female'],
      ethnicities: ['White', 'Mixed', 'Asian or Asian British', 'Black or Black British', 'Other Ethnic Group'],
      languages: ['English', 'Catalan', 'Gujarati', 'Hindi', 'Polish'],
      maritalStatuses: ['Single', 'Married', 'Divorced', 'Not Disclosed'],
      identifier: null,
      nhsNumber: null,
      patientTitle: null,
      patientLastName: null,
      patientFirstName: null,
      patientDOB: null,
      patientDOBpicker: null,
      patientDOBVisible: false,
      patientSex: null,
      patientGender: null,
      patientEthicity: 'White',
      patientLanguage: 'English',
      patientMaritalStatus: 'Divorced',
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
      this.patientDOBpicker = value
      this.patientDOB = internationalDateToUk(value)
      this.patientDOBVisible = false
    },
    async save () {
      if (this.$refs.form.validate()) {
        let patient = this.patientContext
        await createPatient(patient)
        this.$store.commit(mutators.SET_PATIENT, patient)
        this.$router.push({name: 'NewPlan'})
      }
    },
    copyContextFields () {
      if (this.patientContext) {
        this.patientTitle = this.patientContext.name[0].prefix[0]
        this.patientLastName = this.patientContext.name[0].family
        this.patientFirstName = this.patientContext.name[0].given[0]
        this.patientGender = this.patientContext.gender
        this.patientSex = this.patientContext.gender // ?? no field
        this.contactTelephone = this.patientContext.telecom[0].value
        this.identifier = this.patientContext.id
        this.nhsNumber = this.patientContext.identifier[0].value
        this.patientDOB = moment(this.patientContext.birthDate).format('DD-MMM-YYYY')
        this.patientDOBpicker = moment(this.patientContext.birthDate).format('YYYY-MM-DD')
        this.contactAddress1 = this.patientContext.address[0].line[0]
        this.contactAddress2 = this.patientContext.address[0].line[1]
        this.contactAddress3 = this.patientContext.address[0].district
        this.contactTown = this.patientContext.address[0].city
        this.contactPostcode = this.patientContext.address[0].postalCode
        this.contactMobile = this.patientContext.telecom[0].value
        this.contactEmail = this.patientContext.telecom[1].value
      }
    }
  },
  watch: {
    patientContext (value) {
      this.copyContextFields()
    }
  },
  mounted () {
    this.copyContextFields()
    this.$store.commit(mutators.SET_PATIENT, null)
    this.$store.commit(mutators.SET_TREATMENT_PLAN, null)
  }
}
</script>
