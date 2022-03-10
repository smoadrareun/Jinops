<template>
  <div class="maintain">
    <el-card class="box-card1">
       <div slot="header" class="clearfix">
    <span>表格复选框问题</span>
    
  </div>
     <!--  <el-table :data="maintainList" style="width: 100%">
        <el-table-column prop="id" label="自定义id值" width="180">
        </el-table-column>
        <el-table-column prop="building" label="楼盘信息" width="180">
        </el-table-column>
        <el-table-column prop="type" label="维修类型"> </el-table-column>

        <el-table-column prop="result" label="维修状态">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.result==1">完成报修</el-tag>
            <el-tag size="medium" v-if="scope.row.result==2" type="warning">修理中</el-tag>
          </template>
        </el-table-column>
         <el-table-column prop="result" label="操作">
            <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="dialogVisiblelook">查看</el-button>
        <el-button
          size="mini"
          @click="dialogVisiblereply"
         >回复</el-button>
        <el-button
          size="mini"
          type="danger"
         
          >删除</el-button>
      </template>
        </el-table-column>
      </el-table> -->
<xbtable
:list=' maintainList'
:columns='columns'
:operates='operates'
></xbtable>

<xbpagination
@changepagesize='changepage'
@changepage='changepage'
:currentPage.sync='currentPage'
:pagesize.sync='pagesize'
:pagesizes.sync='pagesizes'
:total.sync='total'
></xbpagination>
   <!--    <el-pagination
      
        @size-change="handleSizeChange"
        :current-page="currentPage"      
        @current-change='change'
        :page-size='pagesize'
        :page-sizes='pagesizes'
        class="page"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
</el-pagination> -->
    </el-card>
  <!--   <el-dialog
   append-to-body
  title="提示"
  :visible.sync="dialogVisible1"
  width="30%"
  >
  <span>这是一段信息</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible1 = false">确 定</el-button>
  </span>
</el-dialog>

 <el-dialog
   append-to-body
  title="回复"
  :visible.sync="dialogVisible2"
  width="30%"
  >
  <span>在这里回复用户消息</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible2 = false">确 定</el-button>
  </span>
</el-dialog> -->
  </div>
</template>
<script>
import {
   maintainList,
   totalpage 
        } from "@/api/demo.js";

import xbpagination from '@/components/xb-pagination.vue'
import xbtable from '@/components/xb-table.vue'
import tagobj from '@/components/translate.js' 
export default {
  name: "",
  components: {
      xbpagination,
      xbtable
  
  },
  props: [],
  data() {
    return {
      maintainList: [],
      dialogVisible1:false,
      dialogVisible2:false,
      pagesize:8,
      pagesizes:[2,4,6,8],
      currentPage:1,
      total:0,
      //表格配置项
      //将要变成组件特性的属性
      operates:
      [
      {id:1,lable:'管理',type:'info',size:'mini',methods:(index,row)=>{this.handleManage(index,row)}},
      {id:2,lable:'发布',type:'primary',size:'mini',methods:(index,row)=>{this.handleRelease(index,row)}},
      {id:3,lable:'编辑',type:'warning',size:'mini',methods:(index,row)=>{this.handleEdit(index,row)}},
      {id:4,lable:'删除',type:'danger',size:'mini',methods:(index,row)=>{this.handleDel(index,row)}}
      ],
      columns:[
        {prop:'id',lable:'第一列',align:'center'},
        {prop:'building',lable:'第二列',align:'center'},
           {prop:'result',lable:'第三列',align:'center',type:'tag'},
        {prop:'type',lable:'第四列',align:'center'},
       // {prop:'result',lable:'结果',align:'center',type:'tag'}
      ]
      //return data over
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getmaintainList()
    this.gettotalpage()
   
  },
  mounted() {},
  methods: {
    gettotalpage(){
      totalpage().then(res=>{let a=res.data[0];this.total=a}).catch(err=>{console.log(err)})
    },
    getmaintainList() {
      maintainList({_page:this.currentPage,_limit:this.pagesize}).then((res) => {
        console.log(res)
        console.log(res.data.length)
        this.maintainList=res.data
      });
    },
    //每一页的容量发生改变   已经被优化没用了，将页码改变和页码容量的优化了
   /*  changepagesize(){
     
      this.getmaintainList()
    }, */


    //页码发生变化或页码容量发生变化，一切属性已经在组件之中优化了，
    //只需要渲染更新后返回的列表数据即可
    changepage(){
      
      this.getmaintainList()
    },
    dialogVisiblelook(){
      this.dialogVisible1=true
      
    },
    dialogVisiblereply(){
      this.dialogVisible2=true
    },
    //按钮触发的方法将会触发如下方法

    handleManage(index,row){
      console.log(index)
      console.log(row)
    },
    handleRelease(index,row){
      console.log(index)
      console.log(row)
    },
    handleEdit(index,row){
      console.log(index)
      console.log(row)
    },
    handleDel(index,row){
      console.log(index)
      console.log(row)
    },
    

  },
};
</script>
<style scoped>
.maintain {
  position: relative;
  height: 200%;
}
.box-card1{
  margin-top: 2%;
}
.page{
  margin-left: 30%;
  margin-top: 1%;
}
</style>