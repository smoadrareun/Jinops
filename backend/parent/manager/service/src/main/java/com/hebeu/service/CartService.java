package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Cart;
import com.hebeu.pojo.vo.CartVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartService
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息服务层接口
 */

public interface CartService extends IService<Cart> {

    CartVo getById(String id);

    List<CartVo> getList();

    PageInfo<CartVo> select(CartVo cartVo);

    CartVo insert(CartVo cartVo);

    Boolean delete(String id);

    CartVo update(CartVo cartVo);

}
