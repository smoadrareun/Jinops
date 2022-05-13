package com.hebeu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hebeu.pojo.Cart;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartMapper
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息持久层接口
 */

@Repository
public interface CartMapper extends BaseMapper<Cart> {
}