package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.vo.ClientVo;
import com.hebeu.pojo.ResponseBody;
import com.hebeu.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseBody resp = new ResponseBody();

    @Autowired
    public void setClientService (ClientService clientService) {
        this.clientService = clientService;
    }

    //根据客户id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody getById(@PathVariable("id") Integer id) {
        ClientVo clientVo=clientService.getById(id);
        if(clientVo==null){
            return resp.failure(-1,"根据id查询客户信息失败");
        }else if(clientVo.getId()==null){
            return resp.failure(-404,"根据id查询客户信息未找到数据");
        }else{
            List<ClientVo> list=new ArrayList<>();
            list.add(clientVo);
            return resp.success("根据id查询客户信息成功",list);
        }
    }

    //查询所有客户数据
    @RequestMapping("/getList")
    public ResponseBody getList() {
        List<ClientVo> list=clientService.getList();
        if(list==null){
            return resp.failure(-1,"查询所有客户信息失败");
        }else if(list.size()==0){
            return resp.failure(-404,"查询所有客户信息未找到数据");
        }else{
            return resp.success("查询所有客户信息成功",new PageInfo<>(list));
        }
    }

    //根据条件查询客户数据
    @RequestMapping("/select")
    public ResponseBody find(@RequestBody ClientVo clientVo) {
        List<ClientVo> list=clientService.select(clientVo);
        if(list==null){
            return resp.failure(-1,"根据条件查询客户信息失败");
        }else if(list.size()==0){
            return resp.failure(-404,"根据条件查询客户信息未找到数据");
        }else{
            return resp.success("根据条件查询客户信息成功",new PageInfo<>(list));
        }
    }

    //添加客户数据
    @RequestMapping("/insert")
    public ResponseBody insert(@RequestBody ClientVo clientVo) {
        Boolean aBoolean=clientService.insert(clientVo);
        if(aBoolean==null){
            return resp.failure(-1,"添加客户信息失败");
        }else{
            return resp.success("添加客户信息成功",null);
        }
    }

    //根据客户id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody delete(@PathVariable("id") Integer id) {
        Boolean aBoolean=clientService.delete(id);
        if(aBoolean==null){
            return resp.failure(-1,"删除客户信息失败");
        }else if(!aBoolean){
            return resp.failure(-404,"需删除的客户不存在");
        }else{
            return resp.success("删除客户信息成功",null);
        }
    }

    //修改客户数据
    @RequestMapping("/update")
    public ResponseBody update(@RequestBody ClientVo clientVo) {
        Boolean aBoolean=clientService.update(clientVo);
        if(aBoolean==null){
            return resp.failure(-1,"修改客户信息失败");
        }else if(!aBoolean){
            return resp.failure(-404,"需修改的客户不存在");
        }else{
            return resp.success("修改客户信息成功",null);
        }
    }

}
