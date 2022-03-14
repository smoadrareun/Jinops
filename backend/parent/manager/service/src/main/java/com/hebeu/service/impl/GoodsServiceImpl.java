package com.hebeu.service.impl;

import com.alibaba.fastjson.JSON;
import com.hebeu.common.IDUtil;
import com.hebeu.common.MD5Util;
import com.hebeu.mapper.GoodsMapper;
import com.hebeu.model.GoodsModel;
import com.hebeu.service.GoodsService;
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
 * @ClassName: GoodsServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 商品信息服务层实现类
 */

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {
    
    private GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public List<GoodsModel> getList() {
        List<GoodsModel> list=null;
        try {
            list=goodsMapper.selectGoodsAll();
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
    public GoodsModel getById(Integer id) {
        GoodsModel goodsModel=null;
        try {
            goodsModel=goodsMapper.selectGoodById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(goodsModel==null){
            goodsModel=new GoodsModel();
        }
        return goodsModel;
    }

    @Override
    public List<GoodsModel> find(Map<String,Object> map) {
        List<GoodsModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map), HashMap.class));
        try {
            list=goodsMapper.find(map);
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
    public List<GoodsModel> search(Map<String,Object> map) {
        List<GoodsModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=goodsMapper.search(map);
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
            map.put("id", IDUtil.getID());
            map.put("sold",0);
            System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
            goodsMapper.insert(map);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean insertSpecInfo(Map<String,Object> map) {
        Boolean aBoolean=null;
        try{
            List<GoodsModel.SpecInfo> list=(List) map.get("specInfo");
            List<GoodsModel.SpecInfo> newList=new ArrayList<>();
            for(int i = 0;i<list.size();i++){
                GoodsModel.SpecInfo specInfo = JSON.parseObject(JSON.toJSONString(list.get(i)),GoodsModel.SpecInfo.class);
                if(goodsMapper.selectGoodById(specInfo.getGooId())==null){
                    aBoolean=false;
                    throw new Exception();
                }else{
                    specInfo.setId(IDUtil.getShortUUID());
                    newList.add(specInfo);
                }
            }
            map.put("specInfo",newList);
            System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
            goodsMapper.insertSpecInfo(map);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean delete(Integer id) {
        Boolean aBoolean=null;
        if(goodsMapper.selectGoodById(id)==null){
            aBoolean=false;
        }else{
            try{
                goodsMapper.delete(id);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean deleteSpecInfo(Integer id) {
        Boolean aBoolean=null;
        try{
            goodsMapper.deleteSpecInfo(id);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean update(Map<String,Object> map) {
        Boolean aBoolean=null;
        if(goodsMapper.selectGoodById(Integer.parseInt(map.get("id").toString()))==null){
            aBoolean=false;
        }else if(map.get("num")!=null&&Integer.parseInt(map.get("num").toString())<0){
            aBoolean=false;
        }else{
            try{
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                goodsMapper.update(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean updateSpecInfo(Map<String,Object> map) {
        Boolean aBoolean=null;
        if(goodsMapper.selectGoodById((Integer) map.get("cliId"))==null){
            aBoolean=false;
        }else {
            try {
                System.out.println(JSON.parseObject(JSON.toJSONString(map), HashMap.class));
                goodsMapper.updateSpecInfo(map);
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
        List<GoodsModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            Integer page=1;
            if(map.get("page")!=null&&!map.get("page").equals("")&&map.get("count")!=null&&!map.get("count").equals("")){
                page=Integer.parseInt(map.get("page").toString());
                map.put("page",(page-1)*Integer.parseInt(map.get("count").toString()));
            }else{
                map.put("page",0);
            }
            list=goodsMapper.query(map);
            resultMap.put("goods",list);
            Map<String,Object> newMap = new HashMap<>();
            list=goodsMapper.selectGoodsAll();
            Integer count=list.size();
            Integer count1=count;
            if(map.get("off")!=null){
                if(map.get("off").toString().equals("2")){
                    newMap.put("off",true);
                    list=goodsMapper.find(newMap);
                    count=list.size();
                }else if(map.get("off").toString().equals("1")){
                    newMap.put("off",false);
                    list=goodsMapper.find(newMap);
                    count=list.size();
                }
            }
            if(map.get("num")!=null){
                newMap.put("off",true);
                list=goodsMapper.find(newMap);
                int a=list.size();
                newMap.remove("off");
                newMap.put("num",0);
                list=goodsMapper.find(newMap);
                int b=list.size();
                newMap.put("off",true);
                list=goodsMapper.find(newMap);
                int c=list.size();
                count=count1-(a+b-c);
            }
            if(map.get("count")==null||map.get("count").equals("")){
                map.put("count",count);
            }
            Integer totalPage=(int) Math.ceil((double) count/Double.parseDouble(map.get("count").toString()));
            resultMap.put("page",page);
            resultMap.put("totalPage",totalPage);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return resultMap;
    }

}
