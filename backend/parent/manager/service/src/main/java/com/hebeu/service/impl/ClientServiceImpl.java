package com.hebeu.service.impl;


import com.alibaba.fastjson.JSON;
import com.hebeu.common.IDUtil;
import com.hebeu.common.MD5Util;
import com.hebeu.common.TokenUtil;
import com.hebeu.model.ClientModel;
import com.hebeu.mapper.ClientMapper;
import com.hebeu.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层实现类
 */

@Service
public class ClientServiceImpl implements ClientService {

    private ClientMapper clientMapper;

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public Map<String,Object> login(Map<String,Object> map){
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        Map<String,Object> resultMap=new HashMap<>();
        try{
            Object cliToken=map.get("cliToken");
            if(cliToken!=null){
                String verify=TokenUtil.verify(cliToken.toString(),"kh");
                if(verify!=null){
                    resultMap.put("cliToken",cliToken);
                    map.put("uname",verify);
                    List<ClientModel> list=clientMapper.find(map);
                    list.get(0).setPasswd(null);
                    resultMap.put("client",list.get(0));
                }else{
                    resultMap.put("cliToken","false");
                }
            }
            String passwd=MD5Util.getMD5Str(map.get("passwd").toString());
            map.put("passwd",passwd);
            Integer count=clientMapper.login(map);
            if(count==0){
                resultMap.put("cliToken","false");
            }else if(count==1){
                String str=TokenUtil.getToken(map.get("uname").toString(),passwd,"kh");
                resultMap.put("cliToken",str);
                List<ClientModel> list=clientMapper.find(map);
                list.get(0).setPasswd(null);
                resultMap.put("client",list.get(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public List<ClientModel> getList() {
        List<ClientModel> list=null;
        try {
            list=clientMapper.selectClientAll();
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
    public ClientModel getById(Integer id) {
        ClientModel clientModel=null;
        try {
            clientModel=clientMapper.selectClientById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(clientModel==null){
            clientModel=new ClientModel();
        }
        return clientModel;
    }

    @Override
    public List<ClientModel> find(Map<String,Object> map) {
        List<ClientModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=clientMapper.find(map);
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
    public List<ClientModel> search(Map<String,Object> map) {
        List<ClientModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=clientMapper.search(map);
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
        if(clientMapper.find(hashmap).size()>0){
            aBoolean=false;
        }else{
            try{
                map.put("id",IDUtil.getID());
                map.put("passwd", MD5Util.getMD5Str((String) map.get("passwd")));
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                clientMapper.insert(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean insertAddInfo(Map<String,Object> map) {
        Boolean aBoolean=null;
        try{
            List<ClientModel.AddInfo> list=(List) map.get("addInfo");
            List<ClientModel.AddInfo> newList=new ArrayList<>();
            for(int i = 0;i<list.size();i++){
                ClientModel.AddInfo addInfo = JSON.parseObject(JSON.toJSONString(list.get(i)),ClientModel.AddInfo.class);
                if(clientMapper.selectClientById(addInfo.getCliId())==null){
                    aBoolean=false;
                    throw new Exception();
                }else{
                    addInfo.setId(IDUtil.getShortUUID());
                    newList.add(addInfo);
                }
            }
            map.put("addInfo",newList);
            System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
            clientMapper.insertAddInfo(map);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean delete(Integer id) {
        Boolean aBoolean=null;
        if(clientMapper.selectClientById(id)==null){
            aBoolean=false;
        }else{
            try{
                clientMapper.delete(id);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean deleteAddInfo(Integer id) {
        Boolean aBoolean=null;
        try{
            clientMapper.deleteAddInfo(id);
            aBoolean=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return aBoolean;
    }

    @Override
    public Boolean update(Map<String,Object> map) {
        Boolean aBoolean=null;
        if(clientMapper.selectClientById((Integer) map.get("id"))==null){
            aBoolean=false;
        }else{
            try{
                System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
                if(map.get("passwd")!=null){
                    map.put("passwd", MD5Util.getMD5Str((String) map.get("passwd")));
                }
                clientMapper.update(map);
                aBoolean=true;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

    @Override
    public Boolean updateAddInfo(Map<String,Object> map) {
        Boolean aBoolean=null;
        if(clientMapper.selectClientById((Integer) map.get("cliId"))==null){
            aBoolean=false;
        }else {
            try {
                System.out.println(JSON.parseObject(JSON.toJSONString(map), HashMap.class));
                clientMapper.updateAddInfo(map);
                aBoolean = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return aBoolean;
    }

}
