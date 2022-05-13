package com.hebeu.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.VendorVo;
import com.hebeu.service.VendorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VendorController
 * @Author: Smoadrareun
 * @Description: TODO 商户信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/vendor")
public class VendorController {

    @Resource
    private VendorService vendorService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //商户登录
    @RequestMapping("/login")
    public ResponseBody<Object> login(@RequestBody VendorVo vendorVo) {
        VendorVo vendorVos = vendorService.login(vendorVo.getToken());
        if (ObjectUtils.isEmpty(vendorVos)) {
            vendorVos = vendorService.login(vendorVo.getUname(), vendorVo.getPasswd());
        }
        if (vendorVos == null) {
            return resp.failure(-1, "商户登录失败");
        } else if (vendorVos.getId() == null) {
            return resp.failure(-403, "输入的用户名或密码错误");
        } else {
            return resp.success("商户登录成功", vendorVos);
        }
    }

    //根据商户id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") Integer id) {
        VendorVo vendorVo = vendorService.getById(id);
        if (vendorVo == null) {
            return resp.failure(-1, "根据id查询商户信息失败");
        } else if (vendorVo.getId() == null) {
            return resp.failure(-404, "根据id查询商户信息未找到数据");
        } else {
            return resp.success("根据id查询商户信息成功", vendorVo);
        }
    }

    //查询所有商户数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<VendorVo> list = vendorService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有商户信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有商户信息未找到数据");
        } else {
            return resp.success("查询所有商户信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询商户数据
    @RequestMapping("/select")
    public ResponseBody<Object> select(@RequestBody VendorVo vendorVo) {
        PageInfo<VendorVo> pageInfo = vendorService.select(vendorVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询商户信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询商户信息未找到数据");
        } else {
            return resp.success("根据条件查询商户信息成功", pageInfo);
        }
    }

    //添加商户数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody VendorVo vendorVo) {
        VendorVo vendorVos = vendorService.insert(vendorVo);
        if (vendorVos == null) {
            return resp.failure(-1, "添加商户信息失败");
        } else if (vendorVos.getId() == null) {
            return resp.failure(-400, "输入的用户名已存在");
        } else {
            return resp.success("添加商户信息成功", vendorVos);
        }
    }

    //根据商户id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") Integer id) {
        Boolean aBoolean = vendorService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除商户信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的商户信息不存在");
        } else {
            return resp.success("删除商户信息成功", null);
        }
    }

    //修改商户数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody VendorVo vendorVo) {
        VendorVo vendorVos = vendorService.update(vendorVo);
        if (vendorVos == null) {
            return resp.failure(-1, "修改商户信息失败");
        } else if (vendorVos.getId() == null) {
            return resp.failure(-404, "需修改的商户信息不存在");
        } else {
            return resp.success("修改商户信息成功", vendorVos);
        }
    }

}
