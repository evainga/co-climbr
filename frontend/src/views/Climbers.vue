<script>
import ClimberCard from '@/components/ClimberCard.vue'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'climbers',
  components: {
    ClimberCard
  },
  computed: {
    ...mapState(['climbers'])
  },
  methods: {
    ...mapActions(['fetchClimbers'])
  },
  created () {
    this.fetchClimbers()
  }
}
</script>

<template lang="pug">
  div
    h1 Climbers searching
    section
      climber-card(v-for="climber in climbers", :climber="climber",  v-bind:key="climber.id")

</template>

<style scoped>
  .section {
    padding: 40px 0px;
  }

</style>
