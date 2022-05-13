package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.IDUtil;
import com.hebeu.common.RedisUtil;
import com.hebeu.mapper.CartMapper;
import com.hebeu.pojo.Cart;
import com.hebeu.pojo.vo.CartVo;
import com.hebeu.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息服务层实现类
 */

@Slf4j
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Cart,CartVo> PageInfo<CartVo> pageInfoPoToVo(PageInfo<Cart> pageInfo){
        Page<CartVo> page=new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static CartVo cartPoToVo(Cart cart) {
        if (cart == null) {
            return null;
        }
        CartVo cartVo = new CartVo();
        BeanUtils.copyProperties(cart, cartVo);
        cartVo.setId(cart.getCartId());
        return cartVo;
    }

    public static Cart cartVoToPo(CartVo cartVo) {
        if (cartVo == null) {
            return null;
        }
        Cart cart = new Cart();
        BeanUtils.copyProperties(cartVo, cart);
        cart.setId(null);
        cart.setCartId(cartVo.getId());
        return cart;
    }

    @Override
    public CartVo getById(String id) {
        log.info("根据id查询购物车信息开始，请求参数：{}", id);
        CartVo cartVo = new CartVo();
        try {
            if (redisUtil.hasKey("cart" + id)) {
                String str = String.valueOf(redisUtil.get("cart" + id));
                Type type = new TypeToken<CartVo>() {
                }.getType();
                cartVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", cartVo);
            } else {
                LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Cart::getCartId, id);
                List<Cart> list = cartMapper.selectList(wrapper);
                if (list.size() > 0) {
                    cartVo = cartPoToVo(list.get(0));
                    log.info("根据id查询购物车信息成功：{}", cartVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询购物车信息失败：", e);
            return null;
        }
        return cartVo;
    }

    @Override
    public List<CartVo> getList() {
        log.info("查询所有购物车信息开始");
        List<CartVo> cartVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("cart*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<CartVo>() {
                    }.getType();
                    cartVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
                List<Cart> list = cartMapper.selectList(wrapper);
                for (Cart cart : list) {
                    CartVo cartVo = cartPoToVo(cart);
                    cartVoList.add(cartVo);
                    String json = new Gson().toJson(cartVo);
                    redisUtil.set("cart" + cartVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有购物车信息成功：{}", cartVoList);
        } catch (Exception e) {
            log.error("查询所有购物车信息失败：", e);
            return null;
        }
        return cartVoList;
    }

    @Override
    public PageInfo<CartVo> select(CartVo cartVo) {
        log.info("根据条件查询购物车信息开始，请求参数：{}", cartVo);
        PageInfo<CartVo> pageInfoVo = new PageInfo<>();
        try {
            LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ObjectUtils.isNotEmpty(cartVo.getCliId()), Cart::getCliId, cartVo.getCliId());
            wrapper.eq(ObjectUtils.isNotEmpty(cartVo.getGooId()), Cart::getGooId, cartVo.getGooId());
            wrapper.like(ObjectUtils.isNotEmpty(cartVo.getName()), Cart::getName, cartVo.getName());
            wrapper.eq(ObjectUtils.isNotEmpty(cartVo.getPrice()), Cart::getPrice, cartVo.getPrice());
            wrapper.ge(ObjectUtils.isNotEmpty(cartVo.getMinPrice()), Cart::getPrice, cartVo.getMinPrice());
            wrapper.le(ObjectUtils.isNotEmpty(cartVo.getMaxPrice()), Cart::getPrice, cartVo.getMaxPrice());
            wrapper.orderByAsc(ObjectUtils.isNotEmpty(cartVo.getSort())
                    && cartVo.getSort() == 1, Cart::getPrice);
            wrapper.orderByDesc(ObjectUtils.isNotEmpty(cartVo.getSort())
                    && cartVo.getSort() == 2, Cart::getPrice);
            PageHelper.startPage(ObjectUtils.isNotEmpty(cartVo.getPageNum()) ? cartVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(cartVo.getPageSize()) ? cartVo.getPageSize() : 10);
            List<Cart> list = cartMapper.selectList(wrapper);
            PageInfo<Cart> pageInfo=new PageInfo<>(list);
            pageInfoVo=pageInfoPoToVo(pageInfo);
            for (Cart cart:list){
                pageInfoVo.getList().add(cartPoToVo(cart));
            }
            log.info("根据条件查询购物车信息成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询购物车信息失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartVo insert(CartVo cartVo) {
        log.info("添加购物车信息开始，请求参数：{}", cartVo);
        CartVo cartVos = new CartVo();
        try {
            Cart cart = cartVoToPo(cartVo);
            cart.setCartId(IDUtil.getShortUUID());
            LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Cart::getCartId, cart.getCartId());
            List<Cart> list = cartMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = cartMapper.insert(cart);
                if (count > 0) {
                    cartVos = getById(cart.getCartId());
                    String str = new Gson().toJson(cartVos);
                    redisUtil.set("cart" + cartVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加购物车信息成功：{}",cartVos);
                }
            }
        } catch (Exception e) {
            log.error("添加购物车信息失败：", e);
            return null;
        }
        return cartVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        log.info("删除购物车信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Cart::getCartId, id);
            List<Cart> list = cartMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = cartMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("cart" + id)) {
                        redisUtil.delete("cart" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除购物车信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除购物车信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartVo update(CartVo cartVo) {
        log.info("修改购物车信息开始，请求参数：{}", cartVo);
        CartVo cartVos = new CartVo();
        try {
            Cart cart = cartVoToPo(cartVo);
            LambdaQueryWrapper<Cart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Cart::getCartId, cart.getCartId());
            List<Cart> list = cartMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = cartMapper.update(cart, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("cart" + cart.getCartId())) {
                        redisUtil.delete("cart" + cart.getCartId());
                    }
                    cartVos = getById(cart.getCartId());
                    String str = new Gson().toJson(cartVos);
                    redisUtil.set("cart" + cartVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改购物车信息成功：{}",cartVos);
                }
            }
        } catch (Exception e) {
            log.error("修改购物车信息失败：", e);
            return null;
        }
        return cartVos;
    }

}
