<template>
  <div class="home">
      
<el-container class="container">
  <el-header class="header">Header</el-header>
  <el-container>
    <el-aside height='100%' width="200px" class="aside"><left/></el-aside>
    <el-main class="main"><!-- <router-view/> --><router-view/></el-main>
  </el-container>
</el-container>
  </div>
</template>
<script>
import left from '@/views/aside/index.vue'
export default {
  name: "home",
  components: {left},
  props: [],
  data() {
    return {};
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {},
};
</script>
<style scoped>
.container{
    position: fixed;
    bottom: 0;
    top: 0;
    right: 0;
    left: 0;
}
.main{
    background-color:whitesmoke;
    padding: 0;
}
.aside{
    background-color: rgb(91, 165, 113);
    padding: 0;
}
.header{
    background-color: rgb(199, 66, 29);
    padding: 0;
}
</style>