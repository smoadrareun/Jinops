<template>
  <div class="one">
      <h3>父 组件one</h3>
       <el-button plain @click="click"> 朴素按钮</el-button>
       {{b}}
<two :twodata='user' @hand='handle'/>
<three/>
<four/>
  </div>
</template>
<script>
import{ user } from '@/api/demo.js'
import two from '@/views/two.vue'
import three from '@/views/three.vue'
import four from '@/views/four.vue'

export default {
  name: "one",
  components: {
      two,
      three,
      four
   
  },
  props: {},
  data() {
    return {
      user:[5],
      b:'',
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
     click(){
      user().then(res=>{//console.log(res)
   //   console.log(this.user,'before')
         const arr = res.data
         const a = arr[0]
        const id =a.id
        sessionStorage.setItem('id',id)
         //console.log(a,'sss')
         this.$store.commit('pass',a)
     //    console.log(this.user,'after')
         })
    
   //  this.$router.push('/two')
    },
    handle(a){
     console.log(a)
     this.b=a
    }
  },
};
</script>
<style scoped>

</style>