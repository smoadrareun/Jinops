package com.hebeu.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.ClientVo;
import com.hebeu.service.ClientService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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

    @Resource
    private ClientService clientService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //客户登录
    @RequestMapping("/login")
    public ResponseBody<Object> login(@RequestBody ClientVo clientVo) {
        ClientVo clientVos = clientService.login(clientVo.getToken());
        if (ObjectUtils.isEmpty(clientVos)) {
            clientVos = clientService.login(clientVo.getUname(), clientVo.getPasswd());
        }
        if (clientVos == null) {
            return resp.failure(-1, "客户登录失败");
        } else if (clientVos.getId() == null) {
            return resp.failure(-403, "输入的用户名或密码错误");
        } else {
            return resp.success("客户登录成功", clientVos);
        }
    }

    //根据客户id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") Integer id) {
        ClientVo clientVo = clientService.getById(id);
        if (clientVo == null) {
            return resp.failure(-1, "根据id查询客户信息失败");
        } else if (clientVo.getId() == null) {
            return resp.failure(-404, "根据id查询客户信息未找到数据");
        } else {
            return resp.success("根据id查询客户信息成功", clientVo);
        }
    }

    //查询所有客户数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<ClientVo> list = clientService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有客户信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有客户信息未找到数据");
        } else {
            return resp.success("查询所有客户信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询客户数据
    @RequestMapping("/select")
    public ResponseBody<Object> find(@RequestBody ClientVo clientVo) {
        PageInfo<ClientVo> pageInfo = clientService.select(clientVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询客户信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询客户信息未找到数据");
        } else {
            return resp.success("根据条件查询客户信息成功", pageInfo);
        }
    }

    //添加客户数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody ClientVo clientVo) {
        ClientVo clientVos = clientService.insert(clientVo);
        if (clientVos == null) {
            return resp.failure(-1, "添加客户信息失败");
        } else if (clientVos.getId() == null) {
            return resp.failure(-400, "输入的用户名已存在");
        } else {
            return resp.success("添加客户信息成功", clientVos);
        }
    }

    //添加客户地址信息
    @RequestMapping("/insertAddrInfo")
    public ResponseBody<Object> insertAddrInfo(@RequestBody ClientVo.AddrInfoVo addrInfoVo) {
        ClientVo clientVo = clientService.insertAddrInfo(addrInfoVo);
        if (clientVo == null || clientVo.getId() == null) {
            return resp.failure(-1, "添加客户地址信息失败");
        } else {
            return resp.success("添加客户地址信息成功", clientVo);
        }
    }

    //根据客户id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") Integer id) {
        Boolean aBoolean = clientService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除客户信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的客户信息不存在");
        } else {
            return resp.success("删除客户信息成功", null);
        }
    }

    //根据地址信息id删除数据
    @RequestMapping("/deleteAddrInfo/{id}")
    public ResponseBody<Object> deleteAddrInfo(@PathVariable("id") String id) {
        Boolean aBoolean = clientService.deleteAddrInfo(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除客户地址信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的客户地址信息不存在");
        } else {
            return resp.success("删除客户地址信息成功", null);
        }
    }

    //修改客户数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody ClientVo clientVo) {
        ClientVo clientVos = clientService.update(clientVo);
        if (clientVos == null) {
            return resp.failure(-1, "修改客户信息失败");
        } else if (clientVos.getId() == null) {
            return resp.failure(-404, "需修改的客户信息不存在");
        } else {
            return resp.success("修改客户信息成功", clientVos);
        }
    }

    //修改客户地址信息
    @RequestMapping("/updateAddrInfo")
    public ResponseBody<Object> updateAddrInfo(@RequestBody ClientVo.AddrInfoVo addrInfoVo) {
        ClientVo clientVo = clientService.updateAddrInfo(addrInfoVo);
        if (clientVo == null) {
            return resp.failure(-1, "修改客户地址信息失败");
        } else if (clientVo.getId() == null) {
            return resp.failure(-404, "需修改的客户地址信息不存在");
        } else {
            return resp.success("修改客户地址信息成功", clientVo);
        }
    }

}
