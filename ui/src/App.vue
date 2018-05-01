<template>
  <v-app>
    <v-content>
      <Page/>
    </v-content>
  </v-app>
</template>

<script>
import Page from './components/Page'

export default {
  name: 'App',
  components: {
    Page
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}
</style>
