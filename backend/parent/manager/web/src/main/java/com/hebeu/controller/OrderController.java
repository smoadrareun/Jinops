//package com.hebeu.controller;
//
//import com.hebeu.utils.AssembleResponseMsg;
//import com.hebeu.utils.ResponseBody;
//import com.hebeu.model.OrderModel;
//import com.hebeu.service.OrderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: OrderController
// * @Author: Smoadrareun
// * @Description: TODO 交易信息控制层实现类
// */
//
//@CrossOrigin
//@RestController
//@RequestMapping("/Jinops/order")
//public class OrderController {
//    private OrderService orderService;
//    public AssembleResponseMsg msg = new AssembleResponseMsg();
//
//    @Autowired
//    public void setOrderService (OrderService orderService) {
//        this.orderService = orderService;
//    }
//
//    //查询所有交易数据
//    @RequestMapping("/getList")
//    public ResponseBody getList() {
//        List<OrderModel> list=orderService.getList();
//        if(list==null){
//            return msg.failure(-1,"查询所有交易数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"查询所有交易未找到数据");
//        }else{
//            return msg.success("查询所有交易数据成功","List",list);
//        }
//    }
//
//    //根据交易id查询数据
//    @RequestMapping("/getById/{id}")
//    public ResponseBody getById(@PathVariable("id") String id) {
//        OrderModel orderModel=orderService.getById(id);
//        if(orderModel==null){
//            return msg.failure(-1,"根据交易id查询数据失败");
//        }else if(orderModel.getId()==null){
//            return msg.failure(-404,"根据交易id查询未找到数据");
//        }else{
//            return msg.success("根据交易id查询数据成功","Model",orderModel);
//        }
//    }
//
//    //精确查询交易数据
//    @RequestMapping("/find")
//    public ResponseBody find(@RequestBody Map<String,Object> map) {
//        List<OrderModel> list=orderService.find(map);
//        if(list==null){
//            return msg.failure(-1,"精确查询交易数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"精确查询交易未找到数据");
//        }else{
//            return msg.success("精确查询交易数据成功","List",list);
//        }
//    }
//
//    //模糊查询交易数据
//    @RequestMapping("/search")
//    public ResponseBody search(@RequestBody Map<String,Object> map) {
//        List<OrderModel> list=orderService.search(map);
//        if(list==null){
//            return msg.failure(-1,"模糊查询交易数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"模糊查询交易未找到数据");
//        }else{
//            return msg.success("模糊查询交易数据成功","List",list);
//        }
//    }
//
//    //添加交易数据
//    @RequestMapping("/insert")
//    public ResponseBody insert(@RequestBody Map<String,Object> map) {
//        Map<String,Object> resultMap=orderService.insert(map);
//        if(resultMap.get("id")==null){
//            return msg.failure(-1,"添加交易数据失败");
//        }else{
//            return msg.success("添加交易数据成功","Map",resultMap);
//        }
//    }
//
//    //添加交易详情信息
//    @RequestMapping("/insertComInfo")
//    public ResponseBody insertComInfo(@RequestBody Map<String,Object> map) {
//        Boolean aBoolean=orderService.insertComInfo(map);
//        if(aBoolean==null){
//            return msg.failure(-1,"添加交易详情信息失败");
//        }else if(!aBoolean){
//            return msg.failure(-404,"添加信息的交易不存在");
//        }else{
//            return msg.success("添加交易详情信息成功");
//        }
//    }
//
//    //根据交易id删除数据
//    @RequestMapping("/delete/{id}")
//    public ResponseBody delete(@PathVariable("id") String id) {
//        Boolean aBoolean=orderService.delete(id);
//        if(aBoolean==null){
//            return msg.failure(-1,"删除交易数据失败");
//        }else if(!aBoolean){
//            return msg.failure(-404,"删除的交易不存在");
//        }else{
//            return msg.success("删除交易数据成功");
//        }
//    }
//
//    //根据详情信息id删除数据
//    @RequestMapping("/deleteComInfo/{id}")
//    public ResponseBody deleteComInfo(@PathVariable("id") String id) {
//        Boolean aBoolean=orderService.deleteComInfo(id);
//        if(aBoolean==null){
//            return msg.failure(-1,"删除交易详情信息失败");
//        }else{
//            return msg.success("删除交易详情信息成功");
//        }
//    }
//
//    //修改交易数据
//    @RequestMapping("/update")
//    public ResponseBody update(@RequestBody Map<String,Object> map) {
//        Boolean aBoolean=orderService.update(map);
//        if(aBoolean==null){
//            return msg.failure(-1,"修改交易数据失败");
//        }else if(!aBoolean){
//            return msg.failure(-404,"修改的交易不存在");
//        }else{
//            return msg.success("修改交易数据成功");
//        }
//    }
//
//    //修改交易详情信息
//    @RequestMapping("/updateComInfo")
//    public ResponseBody updateComInfo(@RequestBody Map<String,Object> map) {
//        Boolean aBoolean=orderService.updateComInfo(map);
//        if(aBoolean==null){
//            return msg.failure(-1,"修改交易详情信息失败");
//        }else if(!aBoolean){
//            return msg.failure(-404,"修改的交易不存在");
//        }else{
//            return msg.success("修改交易详情信息成功");
//        }
//    }
//
//    //请求查询订单数据
//    @RequestMapping("/query")
//    public ResponseBody query(@RequestBody Map<String,Object> map) {
//        Map<String,Object> resultMap=orderService.query(map);
//        if(resultMap.get("order")==null){
//            return msg.failure(-1,"请求查询订单数据失败");
//        }else if(((List<OrderModel>) resultMap.get("order")).size()==0){
//            return msg.failure(-404,"请求查询订单未找到数据");
//        }else{
//            return msg.success("请求查询订单数据成功","Map",resultMap);
//        }
//    }
//}
