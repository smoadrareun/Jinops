<template>
  <div class="maintain">
    <el-card class="box-card1">
      <div slot="header" class="clearfix">
    <span>物业管理人员</span>
    
  </div>
      <el-table :data="maintainList" style="width: 100%">
        <el-table-column prop="id" label="自定义id值" width="180">
        </el-table-column>
        <el-table-column prop="building" label="投票信息" width="180">
        </el-table-column>
        <el-table-column prop="type" label="投票类型"> </el-table-column>

        <el-table-column prop="result" label="投票结果">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.result==1">通过</el-tag>
            <el-tag size="medium" v-if="scope.row.result==2" type="warning">未通过</el-tag>
          </template>
        </el-table-column>
         <el-table-column prop="result" label="投票结果操作">
            <template slot-scope="scope">
                <el-button type="primary" size="mini"  @click="dialogVisiblelook">查看</el-button>
        <el-button
          size="mini"
           @click="dialogVisiblereply"
         >公布</el-button>
        <el-button
          size="mini"
          type="danger"
          >删除</el-button>
      </template>
        </el-table-column>
      </el-table>

      <el-pagination
      @current-change='change'
      :page-size=8
      class="page"
  background
  layout="prev, pager, next"
  :total="28">
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
  title="公告"
  :visible.sync="dialogVisible2"
  width="30%"
  >
  <span>关于公告的一些信息，后续可以增删修改</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible2 = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible2 = false">确 定</el-button>
  </span>
</el-dialog>
  </div>
</template>
<script>
import { owners } from "@/api/demo.js";
export default {
  name: "",
  components: {},
  props: [],
  data() {
    return {
      maintainList: [],
       dialogVisible1:false,
      dialogVisible2:false
    };
  },
  computed: {},
  watch: {},
  created() {
    this.getmaintainList();
  },
  mounted() {},
  methods: {
    getmaintainList(_page=1) {
      owners({_page,_limit:8}).then((res) => {
        console.log(res)
        this.maintainList=res.data
      });
    },
    change(page){
      this.getmaintainList(page)
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