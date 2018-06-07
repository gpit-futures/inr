<template>
  <v-app>
    <v-content>
      <v-container fill-height>
        <v-layout column>
        <patient-banner />
        <router-view></router-view>
      </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
import PatientBanner from "./components/PatientBanner";
import mutators from "./store/mutators";

export default {
  name: "App",
  components: {
    PatientBanner
  },
  mounted() {
    console.log("------initialised INR");

    window.isElectron = function() {
      return "Bridge" in window;
    };
    if (isElectron()) {
      console.log("is electron app");
      window.Bridge.updatePatientContext = patient => {
        let event = 'INR patient-context:changed'
        if (patient === null) {
          event = 'INR patient-context:ended'
        }
        console.log(event, patient);
        this.$store.commit(mutators.SET_PATIENT_CONTEXT, patient);
        this.$store.commit(mutators.SET_PATIENT_INR, patient);
      };
    }
  }
};
</script>
