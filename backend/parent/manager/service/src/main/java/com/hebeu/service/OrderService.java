package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Order;
import com.hebeu.pojo.vo.OrderVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: OrderService
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层接口
 */

public interface OrderService extends IService<Order> {

    OrderVo getById(String id);

    List<OrderVo> getList();

    PageInfo<OrderVo> select(OrderVo orderVo);

    OrderVo insert(OrderVo orderVo);

    OrderVo insertComInfo(OrderVo.ComInfoVo comInfoVo);

    Boolean delete(Integer id);

    Boolean deleteComInfo(String id);

    OrderVo update(OrderVo orderVo);

    OrderVo updateComInfo(OrderVo.ComInfoVo comInfoVo);

}
