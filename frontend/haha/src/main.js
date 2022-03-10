import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import '@/styles/index.css'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import * as echarts from 'echarts';  //echart5.x之后不支持原来的引入方法  也就是引入方法升级了
//import dataV from '@jiaminghi/data-view'


Vue.config.productionTip = false
Vue.prototype.$echarts = echarts



Vue.use(ElementUI);
//Vue.use(dataV)



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
