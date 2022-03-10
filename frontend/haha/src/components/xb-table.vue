<template>
  <div class="xbtable">
      <el-table
      ref="xbtable"
      :data="list"
      style="width:100%"
       @selection-change="handleSelectionChange"
      >
      <el-table-column
      type="selection"
      width="55">
    </el-table-column>
      <template v-for="(item,index) in columns">
        
     <el-table-column
     v-if="item.type=='tag'"
     :key="item.lable"
     :prop="item.prop"
     :label="item.lable"
     :align="item.align"
     >
     <template slot-scope="scope" >
       <el-tag
       :type="tagobj[scope.row.result+''].type"
       :size="tagobj[scope.row.result+''].size"
       :effect="tagobj[scope.row.result+''].effect"
       >{{tagobj[scope.row.result+''].lable}}</el-tag>
       </template> 
     </el-table-column>
     
    
      <el-table-column 
      v-else
      :prop="item.prop"
      :key="item.lable"
      :label="item.lable"
      :align="item.align"
      ></el-table-column> 

      
      </template>

      <el-table-column label="操作" align="cneter">
        <template slot-scope="scope">
          <el-button
          v-for="(btn,index) in operates "
          :key="btn.id"
          :size="btn.size"
          :type="btn.type"
          @click.native.prevent="btn.methods(index,scope.row)"
          >
          {{btn.lable}}
          </el-button>
        </template>
      </el-table-column>




      </el-table>
  </div>
</template>
<script>
/* 
小白表格组件介绍：

主要是：提取重复的逻辑形成通用的组件，通过JSON数据结构配置生成

尚未优化成功，还有配置并未完成，待优化状态
其实想想也不需要优化因为项目并不复杂，目前封装的table组件足够使用了
table组件接受三个属性(数组)
这个组件支持三种列，文本列，标签列，按钮列(基本上我们的项目也只需要这三种列，所以目前仅封装这个三个列就ok了)
1.list:填充给table的data属性
2.columns:列
特殊的标签列，需要映射标签对象进行渲染
3.operates:操作,点击事件被native修饰符绑定到父组件，
若是不绑定到如组件，则还要修改组件内部，这样不合适。所以方法应该在父组件实例内完成
*/


import tagobj from '@/components/translate.js' 

export default {
  name: "xbtable",
  components: {},
  //将要变成组件特性的属性
  props: {
    list:Array,
    columns:Array,
    operates:Array
  },
  data() {
    return {
      tagobj:tagobj
    };
  },
  computed: {},
  watch: {},
  created() {
   
  },
  mounted() {},
  methods: {
   handleSelectionChange(data){
     console.log(data,'ss')
 data.forEach(row=>{
  this.$refs.xbtable.toggleRowSelection(row)
}) 
   },
  },
};
</script>
<style scoped>

</style>