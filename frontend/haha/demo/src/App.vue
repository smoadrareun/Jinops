<template>
  <div id="app">
 <!--  <h1>demo</h1>
  <el-button plain @click="click"> 朴素按钮</el-button> -->
    <router-view/>
  </div>
</template>
<script>
import{ user } from '@/api/demo.js'
import {  asyncRoutes } from '@/router'
export default {
  name: "",
  components: {},
  props: {},
  data() {
    return {}; 
  },
  computed: {},
  watch: {},
  created() {
  /*  asyncRoutes() */
  },
  mounted() {},
  methods: {
   /*  click(){
      user().then(res=>{this.$router.push('one')})
   //   this.$router.push('one')
    }, */
  },
};
</script>
<style scoped>
#app{
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
}

</style>
