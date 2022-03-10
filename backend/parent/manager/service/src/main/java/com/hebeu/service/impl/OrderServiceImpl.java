package com.hebeu.service.impl;

import com.alibaba.fastjson.JSON;
import com.hebeu.common.DateUtil;
import com.hebeu.common.IDUtil;
import com.hebeu.model.OrderModel;
import com.hebeu.mapper.OrderMapper;
import com.hebeu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: OrderServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层实现类
 */

@Service
public class OrderServiceImpl implements OrderService {

    private OrderMapper orderMapper;

    @Autowired
    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
    
    @Override
    public List<OrderModel> getList() {
        List<OrderModel> list=null;
        try {
            list=orderMapper.selectOrdersAll();
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
    public OrderModel getById(String id) {
        OrderModel orderModel=null;
        try {
            orderModel=orderMapper.selectOrderById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(orderModel==null){
            orderModel=new OrderModel();
        }
        return orderModel;
    }

    @Override
    public List<OrderModel> find(Map<String,Object> map) {
        List<OrderModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=orderMapper.find(map);
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
    public List<OrderModel> search(Map<String,Object> map) {
        List<OrderModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=orderMapper.search(map);
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
    public Map<String,Object> insert(Map<String,Object> map) {
        Map<String,Object> resultMap=new HashMap<>();
        try{
            String id=IDUtil.getShortUUID();
            map.put("id",id);
            map.put("date", DateUtil.getCurrentTimeMill());
            System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
            orderMapper.insert(map);
            resultMap.put("id",id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public Boolean insertComInfo(Map<String,Object> map) {
        Boolean aBoolean=null;
        try{
            List<OrderModel.ComInfo> list=(List) map.get("comInfo");
            List<OrderModel.ComInfo> newList=new ArrayList<>();
            for(int i = 0;i<list.size();i++){
                OrderModel.ComInfo comInfo = JSON.parseObject(JSON.toJSONString(list.get(i)),OrderModel.ComInfo.class);
                if(orderMapper.selectOrderById(comInfo.getOrdId())==null){
                    aBoolean=false;
                    throw new Exception();
                }else{
                    comInfo.setId(IDUtil.getShortUUID());
                    newList.add(comInfo);
                }
            }
            map.put("comInfo",newList);
            System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
            orderMapper.insertComInfo(map);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean delete(String id) {
        Boolean aBoolean=null;
        if(orderMapper.selectOrderById(id)==null){
            aBoolean=false;
        }else{
            try{
                orderMapper.delete(id);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean deleteComInfo(String id) {
        Boolean aBoolean=null;
        try{
            orderMapper.deleteComInfo(id);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean update(Map<String,Object> map) {
        Boolean aBoolean=null;
        if(orderMapper.selectOrderById((String) map.get("id"))==null){
            aBoolean=false;
        }else{
            try{
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                orderMapper.update(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean updateComInfo(Map<String,Object> map) {
        Boolean aBoolean=null;
        if(orderMapper.selectOrderById((String) map.get("ordId"))==null){
            aBoolean=false;
        }else {
            try {
                System.out.println(JSON.parseObject(JSON.toJSONString(map), HashMap.class));
                orderMapper.updateComInfo(map);
                aBoolean = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Map<String,Object> query(Map<String,Object> map) {
        Map<String,Object> resultMap = new HashMap<>();
        List<OrderModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            Integer page=1;
            if(map.get("page")!=null&&!map.get("page").equals("")&&map.get("count")!=null&&!map.get("count").equals("")){
                page=Integer.parseInt(map.get("page").toString());
                map.put("page",(page-1)*Integer.parseInt(map.get("count").toString()));
            }else{
                map.put("page",0);
            }
            if(map.get("start")!=null&&!"".equals(map.get("start"))){
                map.put("start",DateUtil.DateToTimeMill(map.get("start").toString().replace("T","").replace(" 上午","").replace(" 下午",""),"yyyy-MM-ddHH:mm:ss"));
            }
            if(map.get("end")!=null&&!"".equals(map.get("end"))){
                map.put("end",DateUtil.DateToTimeMill(map.get("end").toString().replace("T","").replace(" 上午","").replace(" 下午",""),"yyyy-MM-ddHH:mm:ss"));
            }
            if(map.get("name")==null||map.get("name").equals("")){
                map.put("name"," ");
            }
            if(map.get("phone")==null||map.get("phone").equals("")){
                map.put("phone",0);
            }
            list=orderMapper.query(map);
            resultMap.put("order",list);
            list=orderMapper.selectOrdersAll();
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
