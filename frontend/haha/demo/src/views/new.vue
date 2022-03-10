<template>
  <div class="newview">
      <mytable
      :tabledata='tabledata'
      :columns='columns'
      :others='others'
       stripe
       border
       highlight-current-row
       @selectionchanged='selectionchange'
      >
<!-- 属性和事件直接绑定在mytable中就可以了 -->
      
      <template v-slot:one="scope">
       <el-button
          size="mini"
          @click="handleEdit(scope)">编辑</el-button>
      </template>
      <template v-slot:three="scope ">
       <el-button
          size="mini"
          @click="handleEdit(scope)">编辑</el-button>
      </template>
       <template v-slot:isheader="scope ">
       <el-input
          
          size="mini"
          placeholder="自定义表头普通列"/>
      </template>
       <template v-slot:isheaderslot="scope ">
       <el-input
          
          size="mini"
          placeholder="插槽列表头"/>
      </template>
       <template v-slot:isneirong="scope ">
      <span>
       <el-input
          
          size="mini"
          placeholder="插槽列内容"/>
      </span>
      </template>
      </mytable>
  </div>
</template>
<script>
import mytable from '@/components/mytable.vue'

export default {
  name: "newview",
  components: {
      mytable
   
  },
  props: {},
  data() {
    return {
      selectionarr : [],

        tabledata:[
          {id:'1',special:3,one:'是一啊',two:'是二啊',three:'是三啊',four:'是四啊',isheader:'自定义表头1'},
         {id:'2',special:6,one:'是一啊',two:'是二啊',three:'是三啊',four:'是四啊',isheader:'自定义表头1'},
         {id:'3',special:1,one:'是一啊',two:'是二啊',three:'是三啊',four:'是四啊',isheader:'自定义表头1'},
          {id:'4',special:7,one:'是一啊',two:'是二啊',three:'是三啊',four:'是四啊',isheader:'自定义表头1'},
          {id:'5',special:2,one:'是一啊',two:'是二啊',three:'是三啊',four:'是四啊',isheader:'自定义表头1'} 
        ],   
        
        
        columns:[
          {attrs:{label:'特殊颜色列',prop:'special',align:'center',width:100,sortable: true},
          isSpecialClass:function(val){
            if(val>5){
return 'red'
            }else{
return 'green'
            }
          },
          isspecial: true,
         },
{attrs:{label:'图标回调列',prop:'icon',align:'center',width:100},isicon: true,iconclass:'el-icon-plus',handlerClick(val){console.log(val)},spanclass:'blue'},
{attrs:{label:'一号表头',prop:'one',align:'center',width:100},isslot:'one'},
{attrs:{label:'二号表头',prop:'two',align:'center',width:100},iscommen: true},
{attrs:{label:'三号表头',prop:'three',align:'center',width:100},isslot:'three'},
{attrs:{label:'四号表头',prop:'four',align:'center'},iscommen: true},
{attrs:{label:'自定义表头',prop:'isheader',align:'center'},isheader: 'isheader'},
{attrs:{prop:'isheaderslot',align:'center'},isheaderslot: 'isheaderslot',isneirong:'isneirong'},
        ],


        others:{
isindex: true,
isselect: true,
isoperation: true,
attrs:{label:'操作列',align:'center',width:300},
data:[
  {
             label: '按钮', // 操作名称             
             type: 'button', //按钮类型icon为图表类型
             attrs:{type:'success',size:'mini'},//按钮的特性
             handleRow: function(a,b,c){console.log(a,b,c)} // 自定义事件
           },
           {
             label: '图标', // 操作名称
            
             type: 'icon', //按钮类型icon为图表类型
             icon:'el-icon-plus',
             handleRow: function(a,b,c){console.log(a,b,c)} // 自定义事件
           }


],
        }
    };
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
   handleEdit(scope){
      console.log(scope)
    },
    spe(zhi){
      if(zhi>5){
return red
      }else{
return green
      }
    },
    selectionchange(selection){
      //this.selectionarr = selection
      console.log(selection,'555')
    }
  },
  
};
</script>

<style scoped>

</style>