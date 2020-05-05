<script>
export default {
  name: 'SearchCard',
  props: ['search']
}
</script>

<template lang="pug">
article.card
    h2.card-title {{ search.location }}, {{search.date}}
    p {{search.initialisingClimberId}}
    p {{search.level}}

</template>

<style scoped>
  .card {
    overflow-x: auto;
    display: inline-block;
    text-align: left;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 3px;
    margin: 10px;
    min-width: 400px;
  }
</style>
