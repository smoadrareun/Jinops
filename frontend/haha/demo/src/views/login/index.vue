<template>
  <div class="login"><h3>login界面</h3>
   <el-button plain @click="loginUers"> 普通用户</el-button>
    <el-button plain @click="loginAdmins"> 超级用户</el-button>
     
     </div>
  
</template>

<script>
import { loginUser , loginAdmin } from '@/api/demo.js' 
import { asyncRoutes } from '@/router'
export default {
  name: "",
  components: {},
  props: [],
  data() {
    return {};
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    loginUers(){
      loginUser().then(res=>{
      let role = res.data[0].role
      this.$store.commit('loginRole',role)
      })
      asyncRoutes()
      this.$router.push('/home')
    },
    loginAdmins(){
      loginAdmin().then(res=>{
        let role = res.data[0].role
        this.$store.commit('loginRole',role) 
      })
       asyncRoutes()
      this.$router.push('/home')
    },
  },
};
</script>
<style scoped>

</style>