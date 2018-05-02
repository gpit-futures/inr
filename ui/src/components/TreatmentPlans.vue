<template>
  <div>
    <v-card flat class="mt-3 pa-2">
      <v-layout row align-baseline>
      <span class="caption">Treatment Plan:</span>
      <v-select
          :items="planDates"
          v-model="selectedPlanDate"
          label="Select"
          single-line
          hide-details
          class="date-select d-inline-block ml-1"
        ></v-select>
      </v-layout>
    </v-card>
    <v-data-table
        :headers="headers"
        :items="items"
        hide-actions
        class="elevation-1">
      <template slot="items" slot-scope="data">
        <td>{{ data.item.testDate }}</td>
        <td>{{ data.item.inr }}</td>
        <td>{{ data.item.dose }}</td>
        <td>{{ data.item.reviewDays }}</td>
        <td>{{ data.item.nextTestDate }}</td>
        <td><a href='#'>Add Comment</a></td>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  name: 'treatment-plans',
  data () {
    return { // Some mock data to fill the page
      planDates: ['01-May-2017', '01-Jan-2016'],
      selectedPlanDate: '01-May-2017',
      headers: [
        {text: 'Test Date', value: 'testDate'},
        {text: 'INR', value: 'inr'},
        {text: 'Dose (mg/day)', value: 'dose'},
        {text: 'Review Days', value: 'reviewDays'},
        {text: 'Next Test Date', value: 'nextTestDate'},
        {text: 'Comments', sortable: false}],
      items: [
        {testDate: '14-April-2017', inr: '2.2', dose: '2.3', reviewDays: 14, nextTestDate: '01-May-2017'},
        {testDate: '01-May-2017', inr: '2.2', dose: '2.4', reviewDays: 21, nextTestDate: '14-May-2017'}]
    }
  }
}
</script>

<style scoped>
.date-select {
  max-width: 160px;
}
</style>
