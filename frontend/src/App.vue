<template>
  <div id="app">
    <div id="nav">
      <router-link to="/">Intro</router-link>
      |
      <router-link to="/searches">Searches</router-link>
      |
      <router-link to="/climbers">Climbers</router-link>
    </div>
    <router-view/>
  </div>
</template>

<script>

if (location.protocol !== 'https:' && process.env.NODE_ENV === 'production') {
  location.replace(`https:${location.href.substring(location.protocol.length)}`)
}

export default {
  name: 'app',
  data () {
    return {
      msg: 'Welcome to co-climbr'
    }
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;
  }

  #nav a {
    font-weight: bold;
    color: #2c3e50;
  }

  #nav a.router-link-exact-active {
    color: #40778e;
  }
</style>
