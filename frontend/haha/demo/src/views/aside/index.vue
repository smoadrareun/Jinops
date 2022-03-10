<template>
  <div class="left">
      <el-menu
      router
      default-active="2"
      class="el-menu-vertical-demo"
     height='100%'
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b">
     <el-submenu
     :index='item.path'
     v-for="item in menulist"
     :key='item.id'>
     <template slot="title">
       <span>{{item.name}}</span>
     </template>
     </el-submenu>
    </el-menu>
  </div>
</template>
<script>
//import store from '@/store'
import map from '@/list.js'
export default {
  name: "left",
  components: {},
  props: [],
  data() {
    return {
      menulist:[],
        map :{
    'user':[
        {id:1,name:'userA',path:'/userA'},{id:2,name:'userB',path:'/auserB'}
    ],
    'admin':[
        {id:1,name:'adminA',path:'/adminA'},{id:2,name:'adminB',path:'/adminB'}
    ]
}
    };
  },
  computed: {},
  watch: {},
  created() {
    let role= this.$store.state.role
  //  console.log(role,'222')
    this.menulist=this.map[role]
  },
  mounted() {},
  methods: {},
};
</script>
<style scoped>
.left{
    height: 100%;
}
.el-menu-vertical-demo{
    height: 100%;
}
</style>