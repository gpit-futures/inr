<template>
  <div>
    <v-card flat class="mt-3 px-2 pb-2">
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

    <table class="tabular">
      <thead>
        <tr class="purple darken-1 white--text">
          <th>Test Date</th>
          <th>INR</th>
          <th>Dose (mg/day)</th>
          <th>Review days</th>
          <th>Next Test Date</th>
          <th>Comments</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="row in items" :key="row.id">
          <td>{{row.testDate}}</td>
          <td>{{row.inr}}</td>
          <td>{{row.dose}}</td>
          <td>{{row.reviewDays}}</td>
          <td>{{row.nextTestDate}}</td>
          <td><a href='#'>Add Comment</a></td>
        </tr>
      </tbody>
    </table>
    <v-btn color="primary">Add New INR <v-icon right>playlist_add</v-icon></v-btn>
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
        {id: 1, testDate: '14-April-2017', inr: '2.2', dose: '2.3', reviewDays: 14, nextTestDate: '01-May-2017'},
        {id: 2, testDate: '01-May-2017', inr: '2.2', dose: '2.4', reviewDays: 21, nextTestDate: '14-May-2017'}]
    }
  }
}
</script>

<style scoped>
.date-select {
  max-width: 160px;
}
.tabular {
  width: 100%;
  border-collapse: collapse
}
.tabular thead tr {
  text-align: center;
}
.tabular tbody tr {
  text-align: center;
}
.tabular th,
.tabular td {
  font-weight: normal;
  padding: 4px;
}
</style>
