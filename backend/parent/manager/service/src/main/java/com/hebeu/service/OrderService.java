package com.hebeu.service;

import com.hebeu.model.OrderModel;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: OrderService
 * @Author: Smoadrareun
 * @Description: TODO 交易信息服务层接口
 */

public interface OrderService {

    List<OrderModel> getList();

    OrderModel getById(String id);

    List<OrderModel> find(Map<String,Object> map);

    List<OrderModel> search(Map<String,Object> map);

    Map<String,Object> insert(Map<String,Object> map);

    Boolean insertComInfo(Map<String,Object> map);

    Boolean delete(String id);

    Boolean deleteComInfo(String id);

    Boolean update(Map<String,Object> map);

    Boolean updateComInfo(Map<String,Object> map);

    Map<String,Object> query(Map<String,Object> map);
}