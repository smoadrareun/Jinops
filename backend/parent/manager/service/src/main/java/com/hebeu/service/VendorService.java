package com.hebeu.service;

import com.hebeu.model.VendorModel;

import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VendorService
 * @Author: Smoadrareun
 * @Description: TODO 商户信息服务层接口
 */

public interface VendorService {

    Map<String,Object> login(Map<String,Object> map);

    List<VendorModel> getList();

    VendorModel getById(Integer id);

    List<VendorModel> find(Map<String,Object> map);

    List<VendorModel> search(Map<String,Object> map);

    Boolean insert(Map<String,Object> map);

    Boolean delete(Integer id);

    Boolean update(Map<String,Object> map);

}