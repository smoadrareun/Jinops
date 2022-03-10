<template>
  <div class="xb-pagination">
        <el-pagination

        @size-change="handleSizeChange"
        @current-change='change'

        :current-page="currentPage"      
        :page-size='pagesize'
        :page-sizes='pagesizes'
        :total="total"

        background
        layout="total, sizes, prev, pager, next, jumper"
        
        >
</el-pagination>
  </div>
</template>
<script>
/* 小白分页组件介绍： 
引入此组件之后需要传递给
1.组件四个属性：

1.currentPage当前页
2.pagesize每页容量
3.pagesizes每页容量数组，用于切换每页容量
4.total数据总条数

2.监听两个方法:这两个事件的回调只需绑定一个重新渲染列表方法即可
1.changepage页码改变事件(当前页更新已经被封装在组件中)
2.changepagesize每页容量改变事件

*/

export default {
  name: "xb-pagination",
  components: {},
  props: {
      currentPage:{
          typeof:Number,
          default:1,
      },
      pagesize:Number,
      pagesizes:Array,
      total:Number
  },
  data() {
    return {
             
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods:{
    change(page){
      this.$emit('update:currentPage',page)
      this.$emit('changepage',page)
    },
    handleSizeChange(value){
      this.$emit('update:pagesize',value)
      this.$emit('update:currentPage',1)
      this.$emit('changepagesize',value)
    },
  }
   
};
</script>
<style scoped>

</style>