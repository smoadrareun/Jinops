package com.hebeu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hebeu.pojo.Order;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: OrderMapper
 * @Author: Smoadrareun
 * @Description: TODO 交易信息持久层接口
 */

@Repository
public interface OrderMapper extends BaseMapper<Order> {
}