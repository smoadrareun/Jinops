<template>
  <div class="three">
       <el-button plain @click="click"> 三号按钮</el-button>
       {{three}}
  </div>
</template>
<script>
import globalbus from '@/utils/globalbus.js'
export default {
  name: "three",
  components: {},
  props: {},
  data() {
    return {
      three:'三号组件中的数据',
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    click(){
globalbus.$emit('global',this.three)
    },
  },
};
</script>
<style scoped>

</style>