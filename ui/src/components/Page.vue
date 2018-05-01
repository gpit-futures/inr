<template>
  <v-container>
    <div class="display-2 text-xs-center mt-2 mb-3">Welcome to your INR App</div>
    <v-data-table
        :headers="headers"
        :items="items"
        hide-actions
        class="elevation-1">
        <template slot="items" slot-scope="data">
          <td>{{ data.item.testDate }}</td>
          <td>{{ data.item.dose }}</td>
          <td>{{ data.item.suggestedDose }}</td>
          <td>{{ data.item.comments }}</td>
        </template>
    </v-data-table>
  </v-container>
</template>

<script>
export default {
  name: 'Page',
  data () {
    return { // Some mock data to fill the page
      headers: [{text: 'Test Date', value: 'testDate'}, {text: 'Dose', value: 'dose'}, {text: 'Suggested Dose', value: 'suggestedDose'}, {text: 'Comments', sortable: false}],
      items: [{testDate: '02-03-2018', dose: '13.5', suggestedDose: '13.5', comments: '123.... testing'},
        {testDate: '12-03-2018', dose: '13.0', suggestedDose: '12.5', comments: 'second comment'}]
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
