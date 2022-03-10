import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    id:JSON.parse(sessionStorage.getItem('a')||'{}'),
    role:sessionStorage.getItem('role')
  },
  mutations: {
    pass(state,a){
      state.id=a
      sessionStorage.setItem('a',JSON.stringify(a))
    },
    loginRole(state,role){
      state.role=role
      sessionStorage.setItem('role',role)
    }
  },
  actions: {
  },
  modules: {
  }
})
