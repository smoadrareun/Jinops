package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.vo.CartVo;
import com.hebeu.pojo.ResponseBody;
import com.hebeu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private CartService cartService;

    public ResponseBody resp = new ResponseBody();

    @Autowired
    public void setCartService (CartService cartService) {
        this.cartService = cartService;
    }

    //根据购物车id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody getById(@PathVariable("id") String id) {
        CartVo cartVo=cartService.getById(id);
        if(cartVo==null){
            return resp.failure(-1,"根据id查询购物车信息失败");
        }else if(cartVo.getId()==null){
            return resp.failure(-404,"根据id查询购物车信息未找到数据");
        }else{
            List<CartVo> list=new ArrayList<>();
            list.add(cartVo);
            return resp.success("根据id查询购物车信息成功",list);
        }
    }

    //查询所有购物车数据
    @RequestMapping("/getList")
    public ResponseBody getList() {
        List<CartVo> list=cartService.getList();
        if(list==null){
            return resp.failure(-1,"查询所有购物车信息失败");
        }else if(list.size()==0){
            return resp.failure(-404,"查询所有购物车信息未找到数据");
        }else{
            return resp.success("查询所有购物车信息成功",new PageInfo<>(list));
        }
    }

    //根据条件查询购物车数据
    @RequestMapping("/select")
    public ResponseBody find(@RequestBody CartVo cartVo) {
        List<CartVo> list=cartService.select(cartVo);
        if(list==null){
            return resp.failure(-1,"根据条件查询购物车信息失败");
        }else if(list.size()==0){
            return resp.failure(-404,"根据条件查询购物车信息未找到数据");
        }else{
            return resp.success("根据条件查询购物车信息成功",new PageInfo<>(list));
        }
    }

    //添加购物车数据
    @RequestMapping("/insert")
    public ResponseBody insert(@RequestBody CartVo cartVo) {
        Boolean aBoolean=cartService.insert(cartVo);
        if(aBoolean==null){
            return resp.failure(-1,"添加购物车信息失败");
        }else{
            return resp.success("添加购物车信息成功",null);
        }
    }

    //根据购物车id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody delete(@PathVariable("id") String id) {
        Boolean aBoolean=cartService.delete(id);
        if(aBoolean==null){
            return resp.failure(-1,"删除购物车信息失败");
        }else if(!aBoolean){
            return resp.failure(-404,"需删除的购物车不存在");
        }else{
            return resp.success("删除购物车信息成功",null);
        }
    }

    //修改购物车数据
    @RequestMapping("/update")
    public ResponseBody update(@RequestBody CartVo cartVo) {
        Boolean aBoolean=cartService.update(cartVo);
        if(aBoolean==null){
            return resp.failure(-1,"修改购物车信息失败");
        }else if(!aBoolean){
            return resp.failure(-404,"需修改的购物车不存在");
        }else{
            return resp.success("修改购物车信息成功",null);
        }
    }

}
