<script>
import SearchCard from '@/components/SearchCard.vue'
import { mapActions, mapState } from 'vuex'

export default {
  name: 'searches',
  components: {
    SearchCard
  },
  computed: {
    ...mapState(['searches'])
  },
  methods: {
    ...mapActions(['fetchSearches'])
  },
  created () {
    this.fetchSearches()
  }
}
</script>

<template lang="pug">
  div
    h1 Searches
    section
      search-card(v-for="search in searches", :search="search",  v-bind:key="search.id")

</template>

<style scoped>
  .section {
    padding: 40px 0px;
  }

</style>
