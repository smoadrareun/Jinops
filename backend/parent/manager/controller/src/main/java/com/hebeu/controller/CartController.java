package com.hebeu.controller;

import com.hebeu.common.AssembleResponseMsg;
import com.hebeu.common.ResponseBody;
import com.hebeu.model.CartModel;
import com.hebeu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public AssembleResponseMsg msg = new AssembleResponseMsg();

    @Autowired
    public void setCartService (CartService cartService) {
        this.cartService = cartService;
    }

    //查询所有购物车数据
    @RequestMapping("/getList")
    public ResponseBody getList() {
        List<CartModel> list=cartService.getList();
        if(list==null){
            return msg.failure(-1,"查询所有购物车数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"查询所有购物车未找到数据");
        }else{
            return msg.success("查询所有购物车数据成功",list);
        }
    }

    //根据购物车id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody getById(@PathVariable("id") String id) {
        CartModel cartModel=cartService.getById(id);
        if(cartModel==null){
            return msg.failure(-1,"根据购物车id查询数据失败");
        }else if(cartModel.getId()==null){
            return msg.failure(-404,"根据购物车id查询未找到数据");
        }else{
            return msg.success("根据购物车id查询数据成功",cartModel);
        }
    }

    //精确查询购物车数据
    @RequestMapping("/find")
    public ResponseBody find(@RequestBody Map<String,Object> map) {
        List<CartModel> list=cartService.find(map);
        if(list==null){
            return msg.failure(-1,"精确查询购物车数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"精确查询购物车未找到数据");
        }else{
            return msg.success("精确查询购物车数据成功",list);
        }
    }

    //模糊查询购物车数据
    @RequestMapping("/search")
    public ResponseBody search(@RequestBody Map<String,Object> map) {
        List<CartModel> list=cartService.search(map);
        if(list==null){
            return msg.failure(-1,"模糊查询购物车数据失败");
        }else if(list.size()==0){
            return msg.failure(-404,"模糊查询购物车未找到数据");
        }else{
            return msg.success("模糊查询购物车数据成功",list);
        }
    }

    //添加购物车数据
    @RequestMapping("/insert")
    public ResponseBody insert(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=cartService.insert(map);
        if(aBoolean==null){
            return msg.failure(-1,"添加购物车数据失败");
        }else{
            return msg.success("添加购物车数据成功");
        }
    }

    //根据购物车id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody delete(@PathVariable("id") String id) {
        Boolean aBoolean=cartService.delete(id);
        if(aBoolean==null){
            return msg.failure(-1,"删除购物车数据失败");
        }else if(!aBoolean){
            return msg.failure(-404,"删除的购物车不存在");
        }else{
            return msg.success("删除购物车数据成功");
        }
    }

    //修改购物车数据
    @RequestMapping("/update")
    public ResponseBody update(@RequestBody Map<String,Object> map) {
        Boolean aBoolean=cartService.update(map);
        if(aBoolean==null){
            return msg.failure(-1,"修改购物车数据失败");
        }else if(!aBoolean){
            return msg.failure(-404,"修改的购物车不存在");
        }else{
            return msg.success("修改购物车数据成功");
        }
    }

    //请求查询购物车数据
    @RequestMapping("/query")
    public ResponseBody query(@RequestBody Map<String,Object> map) {
        Map<String,Object> resultMap=cartService.query(map);
        if(resultMap.get("cart")==null){
            return msg.failure(-1,"请求查询购物车数据失败");
        }else if(((List<CartModel>) resultMap.get("cart")).size()==0){
            return msg.failure(-404,"请求查询购物车未找到数据");
        }else{
            return msg.success("请求查询购物车数据成功",resultMap);
        }
    }

}
