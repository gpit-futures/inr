<template>
  <div class="pt-2">
    <v-tabs color="purple lighten-5" slider-color="purple darken-4" v-model="activeTab">
      <v-tab key="suggested">Suggested</v-tab>
      <v-tab key="current">Current</v-tab>
      <v-tab-item key="suggested">
        <div class="mt-2">
          <div v-for="day in suggestedTreatment" :key="day.day" class="row">
            <v-layout row class="py-1">
              <v-flex xs2 class="text-xs-center">{{day.day}}</v-flex>
              <v-flex xs10>{{day.text}}</v-flex>
            </v-layout>
            <v-divider></v-divider>
          </div>
        </div>
      </v-tab-item>
      <v-tab-item key="current">
        current
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
export default {
  name: 'suggested-plan',
  data () {
    return {
      activeTab: null,
      suggestedTreatment: [
        {day: 'Sunday', text: 'Take 2 x Brown (1mg) [Total 2.0mg]'},
        {day: 'Monday', text: 'Take 2 x Brown (1mg), 1 x White (0.5mg) [Total 2.5mg]'},
        {day: 'Tuesday', text: 'Take 2 x Brown (1mg) [Total 2.0mg]'},
        {day: 'Wednesday', text: 'Take 2 x Brown (1mg), 1 x White (0.5mg) [Total 2.5mg]'},
        {day: 'Thursday', text: 'Take 2 x Brown (1mg) [Total 2.0mg]'},
        {day: 'Friday', text: 'Take 2 x Brown (1mg), 1 x White (0.5mg) [Total 2.5mg]'},
        {day: 'Saturday', text: 'Take 2 x Brown (1mg) [Total 2.0mg]'}
      ]
    }
  }
}
</script>

<style>
.row:hover {
  background-color: #F3E5F5;
}
</style>
