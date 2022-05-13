//package com.hebeu.controller;
//
//import com.hebeu.utils.AssembleResponseMsg;
//import com.hebeu.utils.ResponseBody;
//import com.hebeu.model.VendorModel;
//import com.hebeu.service.VendorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: ClientController
// * @Author: Smoadrareun
// * @Description: TODO 商户信息控制层实现类
// */
//
//@CrossOrigin
//@RestController
//@RequestMapping("/Jinops/vendor")
//public class VendorController {
//    private VendorService vendorService;
//    public AssembleResponseMsg msg = new AssembleResponseMsg();
//
//    @Autowired
//    public void setVendorService (VendorService vendorService) {
//        this.vendorService = vendorService;
//    }
//
//    //商户登录检测
//    @RequestMapping("/login")
//    public ResponseBody login(@RequestBody Map<String,Object> map) {
//        Map<String,Object> resultMap=vendorService.login(map);
//        if(resultMap.get("venToken")==null){
//            return msg.failure(-1,"商户登录失败");
//        }else if("false".equals(resultMap.get("venToken"))){
//            return msg.failure(-403,"用户名或密码错误");
//        }else{
//            return msg.success("商户登录成功","Map",resultMap);
//        }
//    }
//
//    //查询所有商户数据
//    @RequestMapping("/getList")
//    public ResponseBody getList() {
//        List<VendorModel> list=vendorService.getList();
//        if(list==null){
//            return msg.failure(-1,"查询所有商户数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"查询所有商户未找到数据");
//        }else{
//            return msg.success("查询所有商户数据成功","List",list);
//        }
//    }
//
//    //根据商户id查询数据
//    @RequestMapping("/getById/{id}")
//    public ResponseBody getById(@PathVariable("id") Integer id) {
//        VendorModel vendorModel=vendorService.getById(id);
//        if(vendorModel==null){
//            return msg.failure(-1,"根据商户id查询数据失败");
//        }else if(vendorModel.getId()==null){
//            return msg.failure(-404,"根据商户id查询未找到数据");
//        }else{
//            return msg.success("根据商户id查询数据成功","Model",vendorModel);
//        }
//    }
//
//    //精确查询商户数据
//    @RequestMapping("/find")
//    public ResponseBody find(@RequestBody Map<String,Object> map) {
//        List<VendorModel> list=vendorService.find(map);
//        if(list==null){
//            return msg.failure(-1,"精确查询商户数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"精确查询商户未找到数据");
//        }else{
//            return msg.success("精确查询商户数据成功","List",list);
//        }
//    }
//
//    //模糊查询商户数据
//    @RequestMapping("/search")
//    public ResponseBody search(@RequestBody Map<String,Object> map) {
//        List<VendorModel> list=vendorService.search(map);
//        if(list==null){
//            return msg.failure(-1,"模糊查询商户数据失败");
//        }else if(list.size()==0){
//            return msg.failure(-404,"模糊查询商户未找到数据");
//        }else{
//            return msg.success("模糊查询商户数据成功","List",list);
//        }
//    }
//
//    //添加商户数据
//    @RequestMapping("/insert")
//    public ResponseBody insert(@RequestBody Map<String,Object> map) {
//        Boolean aBoolean=vendorService.insert(map);
//        System.out.println(map);
//        if(aBoolean==null){
//            return msg.failure(-1,"添加商户数据失败");
//        }else if(!aBoolean){
//            return msg.failure(-400,"此用户名已存在");
//        }else{
//            return msg.success("添加商户数据成功");
//        }
//    }
//
//    //根据商户id删除数据
//    @RequestMapping("/delete/{id}")
//    public ResponseBody delete(@PathVariable("id") Integer id) {
//        Boolean aBoolean=vendorService.delete(id);
//        if(aBoolean==null){
//            return msg.failure(-1,"删除商户数据失败");
//        }else if(!aBoolean){
//            return msg.failure(-404,"删除的商户不存在");
//        }else{
//            return msg.success("删除商户数据成功");
//        }
//    }
//
//    //修改商户数据
//    @RequestMapping("/update")
//    public ResponseBody update(@RequestBody Map<String,Object> map) {
//        Boolean aBoolean=vendorService.update(map);
//        if(aBoolean==null){
//            return msg.failure(-1,"修改商户数据失败");
//        }else if(!aBoolean){
//            return msg.failure(-404,"修改的商户不存在");
//        }else{
//            return msg.success("修改商户数据成功");
//        }
//    }
//}
