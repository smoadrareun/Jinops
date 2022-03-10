<template>
  <div class="maintain">
    <el-card class="box-card1">
       <div slot="header" class="clearfix">
    <span>物业维修人员</span>
    
  </div>
      <el-table :data="maintainList" style="width: 100%">
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
      </el-table>

      <el-pagination
      
        @size-change="handleSizeChange"
        :current-page="currentPage"      
        @current-change='change'
        :page-size='pagesize'
        :page-sizes='pagesizes'
        class="page"
        background
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
</el-pagination>
    </el-card>
    <el-dialog
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
</el-dialog>
  </div>
</template>
<script>
import {
   maintainList,
   totalpage 
        } from "@/api/demo.js";
export default {
  name: "",
  components: {},
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
    //每一页的容量发生改变
    handleSizeChange(value){
      this.pagesize=value
      this.currentPage=1
      this.getmaintainList()
    },
    //页码发生变化
    change(page){
      this.currentPage=page
      this.getmaintainList()
    },
    dialogVisiblelook(){
      this.dialogVisible1=true
    },
    dialogVisiblereply(){
      this.dialogVisible2=true
    }

  },
};
</script>
<style scoped>
.maintain {
  position: relative;
  height: 100%;
}
.box-card1{
  margin-top: 2%;
}
.page{
  margin-left: 30%;
  margin-top: 1%;
}
</style>