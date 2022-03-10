package com.hebeu.service;

import com.hebeu.model.CartModel;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartService
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息服务层接口
 */

public interface CartService {

    List<CartModel> getList();

    CartModel getById(String id);

    List<CartModel> find(Map<String,Object> map);

    List<CartModel> search(Map<String,Object> map);

    Boolean insert(Map<String,Object> map);

    Boolean delete(String id);

    Boolean update(Map<String,Object> map);

    Map<String,Object> query(Map<String,Object> map);
    
}
