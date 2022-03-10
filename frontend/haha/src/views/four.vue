<template>
  <div class="four">
      <el-button plain @click="click"> 四号按钮</el-button>
      {{four}}
  </div>
</template>
<script>
import globalbus from '@/utils/globalbus.js'
import { login } from "@/api/demo.js";
export default {
  name: "four",
  components: {},
  props: {},
  data() {
    return {
      four:'',
    };
  },
  computed: {},
  watch: {},
  created() {
    globalbus.$on('global',(data)=>{this.four=data})
  },
  mounted() {},
  methods: {
    click(){
      login().then(res=>{console.log(res)})
    }
  },
};
</script>
<style scoped>

</style>