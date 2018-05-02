<template>
  <v-container>
    <div class="display-1 text-xs-center mt-2 mb-3">Welcome to your INR App</div>

    <v-tabs
      v-model="activeTab">
      <v-tab key="plans">Treatment Plans</v-tab>
      <v-tab key="demo">Demographics</v-tab>
      <v-tab key="manage">Patient Management</v-tab>
      <v-tab key="options">Options</v-tab>
      <v-tab key="help">Help</v-tab>
      <v-tab-item key="plans">
        <v-data-table
            :headers="headers"
            :items="items"
            hide-actions
            class="elevation-1 mt-3">
            <template slot="items" slot-scope="data">
              <td>{{ data.item.testDate }}</td>
              <td>{{ data.item.inr }}</td>
              <td>{{ data.item.dose }}</td>
              <td>{{ data.item.reviewDays }}</td>
              <td>{{ data.item.nextTestDate }}</td>
              <td><a href='#'>Add Comment</a></td>
            </template>
        </v-data-table>
      </v-tab-item>
    </v-tabs>
  </v-container>
</template>

<script>
export default {
  name: 'Page',
  data () {
    return { // Some mock data to fill the page
      activeTab: null,
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

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
