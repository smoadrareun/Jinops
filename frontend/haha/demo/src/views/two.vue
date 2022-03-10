<template>
  <div class="two">
    <h3>{{num}}</h3>
      <el-button plain @click="click"> two组件按钮</el-button>
  </div>
</template>
<script>
export default {
  name: "two",
  components: {},
  props: ['twodata'],
    
  
  data() {
    return {
      str:'531'
    };
  },
  computed: {
    num(){return this.$store.state.id}
  },
  watch: {},
  created() {
   //this.num = this.$store.state.id
  },
  mounted() {
    //console.log(this.twodata)
  },
  methods: {
    click(){
          this.$emit('hand',this.str)
    }
  },
};
</script>
<style scoped>

</style>