package com.hebeu.service;

import com.hebeu.model.GoodsModel;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: GoodsService
 * @Author: Smoadrareun
 * @Description: TODO 商品信息服务层接口
 */

public interface GoodsService {

    List<GoodsModel> getList();

    GoodsModel getById(Integer id);

    List<GoodsModel> find(Map<String,Object> map);

    List<GoodsModel> search(Map<String,Object> map);

    Boolean insert(Map<String,Object> map);

    Boolean insertSpecInfo(Map<String,Object> map);

    Boolean delete(Integer id);

    Boolean deleteSpecInfo(Integer id);

    Boolean update(Map<String,Object> map);

    Boolean updateSpecInfo(Map<String,Object> map);

    Map<String,Object> query(Map<String,Object> map);
    
}
