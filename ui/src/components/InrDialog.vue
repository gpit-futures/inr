<template>
  <v-dialog v-model="show" max-width="500px">
    <v-card>
      <v-card-text>
        <v-layout row align-baseline justify-start>
          <div class="caption mr-3">
            Select INR
          </div>
          <v-select
            :items="inrValues"
            v-model="inrValue"
            single-line
            hide-details
            class=" inr-select"
          ></v-select>
        </v-layout>

        <v-text-field
          name="input-1"
          label="Comments"
          textarea
        ></v-text-field>

      </v-card-text>
      <v-card-actions>
        <v-layout row justify-end>
          <v-btn color="primary" flat @click.stop="close">Close</v-btn>
          <v-btn color="primary" flat  @click.stop="submit">Submit</v-btn>
        </v-layout>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: 'inr-dialog',
  props: ['showModal'],
  data () {
    return {
      show: this.showModal,
      inrValue: 1.9,
      inrValues: [1.9, 2.0, 2.1, 2.2, 2.3, 2.4]
    }
  },
  methods: {
    submit () {
      this.$emit('submit')
    },
    close () {
      this.$emit('close')
    }
  },
  watch: {
    showModal (value) {
      this.show = value
    }
  }
}
</script>

<style scoped>
.inr-select {
  max-width: 160px;
}
</style>
