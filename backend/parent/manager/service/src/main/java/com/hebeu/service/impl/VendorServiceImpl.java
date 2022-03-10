package com.hebeu.service.impl;

import com.alibaba.fastjson.JSON;
import com.hebeu.common.IDUtil;
import com.hebeu.common.MD5Util;
import com.hebeu.common.TokenUtil;
import com.hebeu.model.VendorModel;
import com.hebeu.mapper.VendorMapper;
import com.hebeu.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VendorServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 商户信息服务层实现类
 */

@Service
public class VendorServiceImpl implements VendorService {

    private VendorMapper vendorMapper;

    @Autowired
    public void setVendorMapper(VendorMapper vendorMapper) {
        this.vendorMapper = vendorMapper;
    }

    @Override
    public Map<String,Object> login(Map<String,Object> map){
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        Map<String,Object> resultMap=new HashMap<>();
        try{
            Object venToken=map.get("venToken");
            if(venToken!=null){
                String verify=TokenUtil.verify(venToken.toString(),map.get("admin").toString());
                if(verify!=null){
                    resultMap.put("venToken",venToken);
                    map.put("uname",verify);
                    List<VendorModel> list=vendorMapper.find(map);
                    list.get(0).setPasswd(null);
                    resultMap.put("vendor",list.get(0));
                }else{
                    resultMap.put("venToken","false");
                }
            }
            String passwd=MD5Util.getMD5Str(map.get("passwd").toString());
            map.put("passwd",passwd);
            Integer count=vendorMapper.login(map);
            if(count==0){
                resultMap.put("venToken","false");
            }else if(count==1){
                String str=TokenUtil.getToken(map.get("uname").toString(),passwd,map.get("admin").toString());
                resultMap.put("venToken",str);
                List<VendorModel> list=vendorMapper.find(map);
                list.get(0).setPasswd(null);
                resultMap.put("vendor",list.get(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public List<VendorModel> getList() {
        List<VendorModel> list=null;
        try {
            list=vendorMapper.selectVendorAll();
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
    public VendorModel getById(Integer id) {
        VendorModel vendorModel=null;
        try {
            vendorModel=vendorMapper.selectVendorById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(vendorModel==null){
            vendorModel=new VendorModel();
        }
        return vendorModel;
    }

    @Override
    public List<VendorModel> find(Map<String,Object> map) {
        List<VendorModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=vendorMapper.find(map);
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
    public List<VendorModel> search(Map<String,Object> map) {
        List<VendorModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=vendorMapper.search(map);
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
        Map<String,Object> hashmap = new HashMap<>();
        hashmap.put("uname",map.get("uname"));
        hashmap.put("admin",map.get("admin"));
        if(vendorMapper.find(hashmap).size()>0){
            aBoolean=false;
        }else{
            try{
                map.put("id",IDUtil.getID());
                map.put("passwd", MD5Util.getMD5Str((String) map.get("passwd")));
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                vendorMapper.insert(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean delete(Integer id) {
        Boolean aBoolean=null;
        if(vendorMapper.selectVendorById(id)==null){
            aBoolean=false;
        }else{
            try{
                vendorMapper.delete(id);
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
        if(vendorMapper.selectVendorById((Integer) map.get("id"))==null){
            aBoolean=false;
        }else{
            try{
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                if(map.get("passwd")!=null){
                    map.put("passwd", MD5Util.getMD5Str((String) map.get("passwd")));
                }
                vendorMapper.update(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

}
