//package com.hebeu.controller;
//
//import com.alibaba.fastjson.JSONArray;
//import com.hebeu.utils.AssembleResponseMsg;
//import com.hebeu.utils.ResponseBody;
//import com.hebeu.model.LogsModel;
//import com.hebeu.service.LogsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: LogsController
// * @Author: Smoadrareun
// * @Description: TODO 日志信息控制层实现类
// */
//
//@CrossOrigin
//@RestController
//@RequestMapping("/Jinops/logs")
//public class LogsController {
//    private LogsService logsService;
//    public AssembleResponseMsg msg = new AssembleResponseMsg();
//
//    @Autowired
//    public void setLogsService (LogsService logsService) {
//        this.logsService = logsService;
//    }
//
//    //查询所有日志数据
//    @RequestMapping("/getList")
//    public ResponseBody getList() {
//        List<LogsModel> list=logsService.getList();
//        if(list==null){
//            return msg.failure(-1,"查询所有日志数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"查询所有日志未找到数据");
//        }else{
//            return msg.success("查询所有日志数据成功","List",list);
//        }
//    }
//
//    //根据日志id查询数据
//    @RequestMapping("/getById/{id}")
//    public ResponseBody getById(@PathVariable("id") String id) {
//        LogsModel logsModel=logsService.getById(id);
//        if(logsModel==null){
//            return msg.failure(-1,"根据日志id查询数据失败");
//        }else if(logsModel.getId()==null){
//            return msg.failure(-404,"根据日志id查询未找到数据");
//        }else{
//            return msg.success("根据日志id查询数据成功","Model",logsModel);
//        }
//    }
//
//    //精确查询日志数据
//    @RequestMapping("/find")
//    public ResponseBody find(@RequestBody Map<String,Object> map) {
//        List<LogsModel> list=logsService.find(map);
//        if(list==null){
//            return msg.failure(-1,"精确查询日志数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"精确查询日志未找到数据");
//        }else{
//            return msg.success("精确查询日志数据成功","List",list);
//        }
//    }
//
//    //模糊查询日志数据
//    @RequestMapping("/search")
//    public ResponseBody search(@RequestBody Map<String,Object> map) {
//        List<LogsModel> list=logsService.search(map);
//        if(list==null){
//            return msg.failure(-1,"模糊查询日志数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"模糊查询日志未找到数据");
//        }else{
//            return msg.success("模糊查询日志数据成功","List",list);
//        }
//    }
//
//    //请求查询日志数据
//    @RequestMapping("/query")
//    public ResponseBody query(@RequestBody Map<String,Object> map) {
//        Map<String,Object> resultMap=logsService.query(map);
//        if(resultMap.get("logs")==null){
//            return msg.failure(-1,"请求查询日志数据失败");
//        }else if(((List<LogsModel>) resultMap.get("logs")).size()==0){
//            return msg.failure(-404,"请求查询日志未找到数据");
//        }else{
//            return msg.success("请求查询日志数据成功","Map",resultMap);
//        }
//    }
//
//    @RequestMapping("/kind/{kind}")
//    public ResponseBody kind(@PathVariable("kind") Integer kind) {
//        JSONArray jsonArray=logsService.kind(kind);
//        if(jsonArray==null){
//            return msg.failure(-1,"请求查询日志数据失败");
//        }else{
//            return msg.success("请求查询日志数据成功","JSONArray",jsonArray);
//        }
//    }
//
//}
