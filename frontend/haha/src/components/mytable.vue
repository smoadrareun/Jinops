 <template>
 <div class="mytable">
 <el-table
 ref="mytable"
 v-bind='$attrs'
 v-on = '$listeners'
  :select-on-indeterminate="false"
 :data="tabledata"
 @select="handleSelectionChange"
 @row-click="rowClick"
 @select-all='clickall'
  @selection-change="selectionchanged"
 >

<!-- 是否有多选 -->
 <el-table-column
 v-if="others.isselect"
 type="selection"
 :width="55"
 align="center"
 
 >

 </el-table-column>
<!-- 是否需要序号 -->
 <el-table-column
 v-if="others.isindex"
 type="index"
 label="序号"
 width="55"
 align="center"
 >

</el-table-column>


<!-- 接下来是非功能列，非功能列分为插槽列和非插槽列，非插槽列可以再加上常见的列 -->
<template v-for="column in columns">

  <!-- 特殊颜色的列 -->
  <el-table-column
  v-if="column.isspecial"
  :key="column.attrs.prop"
  v-bind="column.attrs || {}"
  >
 <template slot-scope="scope">
           <span :class="column.isSpecialClass(scope.row[scope.column.property])">{{ scope.row[scope.column.property] }}</span>
         </template>


  </el-table-column>

<!-- 带图标的列包含回调函数 -->
<el-table-column
v-if="column.isicon"
:key="column.attrs.prop"
v-bind="column.attrs || {}"
>
 <template slot-scope="scope">
            <span :class="column.spanclass">
              
              <i  :class='column.iconclass' @click="column.handlerClick(scope.row)" />
            </span>
            <!-- 比如要输入框 显示图片等等 自己定义 -->
            <!-- <slot :name="item.prop" :scope="scope" /> --><!--   暂时不需要插槽 -->
          </template>
        </el-table-column>


  <!-- 自定义表头 -->
 <el-table-column
 v-if="column.isheader"
 :key="column.attrs.prop"
 v-bind="column.attrs || {}">

 <template slot="header" slot-scope="scope">

<slot :name="column.isheader" :scope='scope'/>
 
 </template>

</el-table-column>
 <!-- 自定义表头加插槽列 -->
 <el-table-column
 v-if="column.isheaderslot"
 :key="column.attrs.prop"
 v-bind="column.attrs || {}">

 <template slot="header" slot-scope="scope">

<slot :name="column.isheaderslot" :scope='scope'/>
 
 </template>


<template slot-scope="scope">

<slot :name="column.isneirong" :scope='scope'/>
 
 </template>

</el-table-column>

  <!-- 常规列 -->
<el-table-column
v-if="column.iscommen"
 :key='column.attrs.prop'
v-bind="column.attrs|| {}"
>

</el-table-column>


<!-- 插槽列 -->
 <el-table-column
 v-if="column.isslot"
 :key="column.attrs.prop"
 v-bind="column.attrs || {}">

<template slot-scope="scope">

<slot :name="column.isslot" :scope='scope'/>
 
 </template>

</el-table-column>



 </template>
<!-- 操作列：这里为操作列先写两种情况，按钮或图标 -->
<!-- 按钮情况 -->
<el-table-column
 v-if='others.isoperation'
 fixed="right"
 v-bind="others.attrs"
 >
 <template slot-scope="scope" >
   <div class="btn">
             <div v-for="item in others.data" :key="item.label">
             <template v-if="item.type!='icon'">
               <el-button
                v-bind="item.attrs"
                 @click.native.prevent="item.handleRow(scope.$index, scope.row, item.label)"
               >
                  {{ item.label }}
               </el-button>
             </template>
             <template v-else>
 <i :class="item.icon"  @click="item.handleRow(scope.$index, scope.row, item.label)" />
             </template>
             </div>
   </div>
 </template>
</el-table-column>


 </el-table>
  </div>
</template>
<script>
export default {
  name: "mytable",
  components: {},
  props: {
    tabledata:Array,
    columns:Array,
    others:Object

  },
  data() {
    return {
      multipleSelection : [],
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    rowClick(row, column, event) {  // 点击行多选
        // console.log(row)
        // 从已选中数据中 判断当前点击的是否被选中
        const selected = this.multipleSelection.some(item => item.id === row.id)  // 是取消选择还是选中
        if (!selected) { // 不包含   代表选择
          this.multipleSelection.push(row)
          this.$refs['mytable'].toggleRowSelection(row, true);
        } else { // 取消选择
          var finalArr = this.multipleSelection.filter((item) => {
            return item.id !== row.id
          })
          this.multipleSelection = finalArr  // 取消后剩余选中的

          this.$refs['mytable'].toggleRowSelection(row, false);
        }
        console.log('更改选择后', this.multipleSelection)
    },
    handleSelectionChange(selection, row){
      const selected = this.multipleSelection.some(item => item.id === row.id)  // 是取消选择还是选中
      if(!selected){
 this.multipleSelection.push(row)
  console.log(selection,1)
      }else{
let finalArr = this.multipleSelection.filter((item) => {
            return item.id !== row.id
          })
          this.multipleSelection = finalArr  // 取消后剩余选中的
         //  this.$refs['mytable'].toggleRowSelection(row, false)
         console.log(selection,2)

      }
    },
    clickall(selection){
     // console.log(selection)
      if(!selection==[])
      {
        console.log(111)
        console.log(selection)
this.multipleSelection = selection
console.log(this.multipleSelection)
    }else{
      console.log(selection)
this.multipleSelection = []
selection = []
    }
  },
  selectionchanged(selection){
this.$emit('selectionchanged',selection)
  },
}
}
</script>
<style scoped>
.red{
  color: red;
}
.green{
  color: green;
}



</style>
<style scoped src='@/styles/icon.css'>

</style>  