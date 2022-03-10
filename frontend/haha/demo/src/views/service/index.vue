<template>
  <div class="maintain">
    <el-card class="box-card1">
      <div slot="header" class="clearfix">
    <span>物业服务人员</span>
    
  </div>
      <el-table :data="maintainList" style="width: 100%">
        <el-table-column prop="id" label="自定义id值" width="180">
        </el-table-column>
        <el-table-column prop="building" label="区域信息" width="180">
        </el-table-column>
        <el-table-column prop="type" label="待定表格项"> </el-table-column>

        <el-table-column prop="result" label="待定状态">
          <template slot-scope="scope">
            <el-tag size="medium" v-if="scope.row.result==1">成功标签</el-tag>
            <el-tag size="medium" v-if="scope.row.result==2" type="warning">失败标签</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="result" label="分配权限">
            <template slot-scope="scope">
        <el-button
          size="mini"
          @click="dialogVisiblelook"
         >一号角色</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="dialogVisiblelook">
          二号角色
          </el-button>
           <el-button
          size="mini"
          @click="dialogVisiblelook"
         >三号角色</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="dialogVisiblelook">
          四号角色
          </el-button>
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
  title="角色界面"
  :visible.sync="dialogVisible1"
  width="30%"
  >
  <span>分配角色</span>
  <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible1 = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible1 = false">确 定</el-button>
  </span>
</el-dialog>
  </div>
</template>
<script>
import { service } from "@/api/demo.js";
export default {
  name: "",
  components: {},
  props: [],
  data() {
    return {
      maintainList: [],
       dialogVisible1:false,
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
      service({_page,_limit:8}).then((res) => {
        console.log(res)
        this.maintainList=res.data
      });
    },
    change(page){
      this.getmaintainList(page)
    },
    dialogVisiblelook(){
      this.dialogVisible1=true
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