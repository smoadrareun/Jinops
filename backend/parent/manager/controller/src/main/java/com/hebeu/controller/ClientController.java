package com.hebeu.controller;

import com.hebeu.common.AssembleResponseMsg;
import com.hebeu.common.ResponseBody;
import com.hebeu.model.ClientModel;
import com.hebeu.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientController
 * @Author: Smoadrareun
 * @Description: TODO 客户信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/client")
public class ClientController {
    private ClientService clientService;
    public AssembleResponseMsg msg = new AssembleResponseMsg();

    @Autowired
    public void setClientService (ClientService clientService) {
        this.clientService = clientService;
    }

    //客户登录检测
    @RequestMapping("/login")
    public ResponseBody login(@RequestBody Map<String,Object> map) {
        Map<String,Object> resultMap=clientService.login(map);
        if(resultMap.get("cliToken")==null){
            return msg.failure(-1,"客户登录失败");
        }else if("false".equals(resultMap.get("cliToken"))){
            return msg.failure(-403,"用户名或密码错误");
        }else{
            return msg.success("客户登录成功",resultMap);
        }
    }

    //查询所有客户数据
    @RequestMapping("/getList")
    public ResponseBody getList() {
        List<ClientModel> list=clientService.getList();
        if(list==null){
            return msg.failure(-1,"查询所有客户数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"查询所有客户未找到数据");
        }else{
            return msg.success("查询所有客户数据成功",list);
        }
    }

    //根据客户id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody getById(@PathVariable("id") Integer id) {
        ClientModel clientModel=clientService.getById(id);
        if(clientModel==null){
            return msg.failure(-1,"根据客户id查询数据失败");
        }else if(clientModel.getId()==null){
            return msg.failure(-404,"根据客户id查询未找到数据");
        }else{
            return msg.success("根据客户id查询数据成功",clientModel);
        }
    }

    //精确查询客户数据
    @RequestMapping("/find")
    public ResponseBody find(@RequestBody Map<String,Object> map) {
        List<ClientModel> list=clientService.find(map);
        if(list==null){
            return msg.failure(-1,"精确查询客户数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"精确查询客户未找到数据");
        }else{
            return msg.success("精确查询客户数据成功",list);
        }
    }

    //模糊查询客户数据
    @RequestMapping("/search")
    public ResponseBody search(@RequestBody Map<String,Object> map) {
        List<ClientModel> list=clientService.search(map);
        if(list==null){
            return msg.failure(-1,"模糊查询客户数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"模糊查询客户未找到数据");
        }else{
            return msg.success("模糊查询客户数据成功",list);
        }
    }

    //添加客户数据
    @RequestMapping("/insert")
    public ResponseBody insert(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=clientService.insert(map);
        if(aBoolean==null){
            return msg.failure(-1,"添加客户数据失败");
        }else if(!aBoolean){
            return msg.failure(-400,"此用户名已存在");
        }else{
            return msg.success("添加客户数据成功");
        }
    }

    //添加客户地址信息
    @RequestMapping("/insertAddInfo")
    public ResponseBody insertAddInfo(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=clientService.insertAddInfo(map);
        if(aBoolean==null){
            return msg.failure(-1,"添加客户地址信息失败");
        }else if(!aBoolean){
            return msg.failure(-404,"添加信息的客户不存在");
        }else{
            return msg.success("添加客户地址信息成功");
        }
    }

    //根据客户id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody delete(@PathVariable("id") Integer id) {
        Boolean aBoolean=clientService.delete(id);
        if(aBoolean==null){
            return msg.failure(-1,"删除客户数据失败");
        }else if(!aBoolean){
            return msg.failure(-404,"删除的客户不存在");
        }else{
            return msg.success("删除客户数据成功");
        }
    }

    //根据地址信息id删除数据
    @RequestMapping("/deleteAddInfo/{id}")
    public ResponseBody deleteAddInfo(@PathVariable("id") Integer id) {
        Boolean aBoolean=clientService.deleteAddInfo(id);
        if(aBoolean==null){
            return msg.failure(-1,"删除客户地址信息失败");
        }else{
            return msg.success("删除客户地址信息成功");
        }
    }

    //修改客户数据
    @RequestMapping("/update")
    public ResponseBody update(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=clientService.update(map);
        if(aBoolean==null){
            return msg.failure(-1,"修改客户数据失败");
        }else if(!aBoolean){
            return msg.failure(-404,"修改的客户不存在");
        }else{
            return msg.success("修改客户数据成功");
        }
    }

    //修改客户地址信息
    @RequestMapping("/updateAddInfo")
    public ResponseBody updateAddInfo(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=clientService.updateAddInfo(map);
        if(aBoolean==null){
            return msg.failure(-1,"修改客户地址信息失败");
        }else if(!aBoolean){
            return msg.failure(-404,"修改的客户不存在");
        }else{
            return msg.success("修改客户地址信息成功");
        }
    }
}
