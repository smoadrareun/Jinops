package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.CartVo;
import com.hebeu.service.CartService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartController
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/cart")
public class CartController {

    @Resource
    private CartService cartService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //根据购物车id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") String id) {
        CartVo cartVo = cartService.getById(id);
        if (cartVo == null) {
            return resp.failure(-1, "根据id查询购物车信息失败");
        } else if (cartVo.getId() == null) {
            return resp.failure(-404, "根据id查询购物车信息未找到数据");
        } else {
            return resp.success("根据id查询购物车信息成功", cartVo);
        }
    }

    //查询所有购物车数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<CartVo> list = cartService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有购物车信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有购物车信息未找到数据");
        } else {
            return resp.success("查询所有购物车信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询购物车数据
    @RequestMapping("/select")
    public ResponseBody<Object> select(@RequestBody CartVo cartVo) {
        PageInfo<CartVo> pageInfo = cartService.select(cartVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询购物车信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询购物车信息未找到数据");
        } else {
            return resp.success("根据条件查询购物车信息成功", pageInfo);
        }
    }

    //添加购物车数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody CartVo cartVo) {
        CartVo cartVos = cartService.insert(cartVo);
        if (cartVos == null || cartVos.getId() == null) {
            return resp.failure(-1, "添加购物车信息失败");
        } else {
            return resp.success("添加购物车信息成功", cartVos);
        }
    }

    //根据购物车id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") String id) {
        Boolean aBoolean = cartService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除购物车信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的购物车信息不存在");
        } else {
            return resp.success("删除购物车信息成功", null);
        }
    }

    //修改购物车数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody CartVo cartVo) {
        CartVo cartVos = cartService.update(cartVo);
        if (cartVos == null) {
            return resp.failure(-1, "修改购物车信息失败");
        } else if (cartVos.getId() == null) {
            return resp.failure(-404, "需修改的购物车信息不存在");
        } else {
            return resp.success("修改购物车信息成功", null);
        }
    }

}
