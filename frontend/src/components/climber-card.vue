<script>
export default {
  name: 'ClimberCard',
  props: ['climber']
}
</script>

<template lang="pug">
article.card
    img(:src="`https://picsum.photos/300/200?random=${climber._id}`", alt="random picture")
    h2.card-title {{ climber.name }}
    p {{climber.level}}

</template>

<style scoped>
  .card {
    overflow-x: auto;
    display: inline-block;
    text-align: left;
    padding: 20px;
    border: 1px solid #ddd;
    border-radius: 3px;
    margin: 20px;
    min-width: 200px;
    min-height: 340px;
  }
</style>
