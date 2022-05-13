package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Vendor;
import com.hebeu.pojo.vo.VendorVo;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VendorService
 * @Author: Smoadrareun
 * @Description: TODO 商户信息服务层接口
 */

public interface VendorService extends IService<Vendor> {

    VendorVo login(String uname, String passwd);

    VendorVo login(String token);

    VendorVo getById(Integer id);

    List<VendorVo> getList();

    PageInfo<VendorVo> select(VendorVo clientVo);

    VendorVo insert(VendorVo clientVo);

    Boolean delete(Integer id);

    VendorVo update(VendorVo clientVo);

}