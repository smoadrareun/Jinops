package com.hebeu.service.impl;

import com.alibaba.fastjson.JSON;
import com.hebeu.common.IDUtil;
import com.hebeu.mapper.CartMapper;
import com.hebeu.model.CartModel;
import com.hebeu.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息服务层实现类
 */

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public List<CartModel> getList() {
        List<CartModel> list=null;
        try {
            list=cartMapper.selectCartAll();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public CartModel getById(String id) {
        CartModel cartModel=null;
        try {
            cartModel=cartMapper.selectCartById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(cartModel==null){
            cartModel=new CartModel();
        }
        return cartModel;
    }

    @Override
    public List<CartModel> find(Map<String,Object> map) {
        List<CartModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map), HashMap.class));
        try {
            list=cartMapper.find(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<CartModel> search(Map<String,Object> map) {
        List<CartModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=cartMapper.search(map);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(list==null){
            list=new ArrayList<>();
        }
        return list;
    }

    @Override
    public Boolean insert(Map<String,Object> map) {
        Boolean aBoolean=null;
        try{
            map.put("id", IDUtil.getShortUUID());
            System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
            cartMapper.insert(map);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean delete(String id) {
        Boolean aBoolean=null;
        if(cartMapper.selectCartById(id)==null){
            aBoolean=false;
        }else{
            try{
                cartMapper.delete(id);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean update(Map<String,Object> map) {
        Boolean aBoolean=null;
        System.out.println(map.get("id").toString());
        if(cartMapper.selectCartById(map.get("id").toString())==null){
            aBoolean=false;
        }else{
            try{
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                cartMapper.update(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Map<String,Object> query(Map<String,Object> map) {
        Map<String,Object> resultMap = new HashMap<>();
        List<CartModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            Integer page=1;
            if(map.get("page")!=null&&!map.get("page").equals("")&&map.get("count")!=null&&!map.get("count").equals("")){
                page=Integer.parseInt(map.get("page").toString());
                map.put("page",(page-1)*Integer.parseInt(map.get("count").toString()));
            }else{
                map.put("page",0);
            }
            list=cartMapper.query(map);
            resultMap.put("cart",list);
            list=cartMapper.selectCartAll();
            if(map.get("count")==null||map.get("count").equals("")){
                map.put("count",list.size());
            }
            Integer totalPage=(int) Math.ceil((double) list.size()/Double.parseDouble(map.get("count").toString()));
            resultMap.put("page",page);
            resultMap.put("totalPage",totalPage);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return resultMap;
    }

}
