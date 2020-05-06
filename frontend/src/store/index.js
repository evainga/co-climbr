import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    climbers: [],
    searches: []
  },
  mutations: {
    SET_CLIMBERS (state, data) {
      state.climbers = data
    },
    SET_SEARCHES (state, data) {
      state.searches = data
    }
  },
  actions: {
    async fetchClimbers ({ commit }) {
      const result = await axios.get('http://localhost:8080/data/climbers')
      commit('SET_CLIMBERS', result.data)
    },
    async fetchSearches ({ commit }) {
      const result = await axios.get('http://localhost:8080/data/searches')
      commit('SET_SEARCHES', result.data)
    }
  },
  modules: {}
})
