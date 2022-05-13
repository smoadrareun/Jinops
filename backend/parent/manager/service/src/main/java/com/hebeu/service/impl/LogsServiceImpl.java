package com.hebeu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hebeu.common.DateUtil;
import com.hebeu.mapper.LogsMapper;
import com.hebeu.model.LogsModel;
import com.hebeu.service.LogsService;
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
 * @ClassName: LogsServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 日志信息服务层实现类
 */

@Service
@Transactional
public class LogsServiceImpl implements LogsService {

    private LogsMapper logsMapper;

    @Autowired
    public void setLogsMapper(LogsMapper logsMapper) {
        this.logsMapper = logsMapper;
    }

    @Override
    public List<LogsModel> getList() {
        List<LogsModel> list=null;
        try {
            list=logsMapper.selectLogsAll();
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
    public LogsModel getById(String id) {
        LogsModel logsModel=null;
        try {
            logsModel=logsMapper.selectLogsById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        if(logsModel==null){
            logsModel=new LogsModel();
        }
        return logsModel;
    }

    @Override
    public List<LogsModel> find(Map<String,Object> map) {
        List<LogsModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map), HashMap.class));
        try {
            list=logsMapper.find(map);
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
    public List<LogsModel> search(Map<String,Object> map) {
        List<LogsModel> list=null;
        System.out.println(JSON.parseObject(JSON.toJSONString(map),HashMap.class));
        try {
            list=logsMapper.search(map);
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
    public Map<String,Object> query(Map<String,Object> map) {
        Map<String,Object> resultMap = new HashMap<>();
        List<LogsModel> list=null;
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
                map.put("start", DateUtil.DateToTimeMill(map.get("start").toString().replace("T","").replace(" 上午","").replace(" 下午",""),"yyyy-MM-ddHH:mm:ss"));
            }
            if(map.get("end")!=null&&!"".equals(map.get("end"))){
                map.put("end",DateUtil.DateToTimeMill(map.get("end").toString().replace("T","").replace(" 上午","").replace(" 下午",""),"yyyy-MM-ddHH:mm:ss"));
            }
            System.out.println(map.get("start"));
            System.out.println(map.get("end"));
            list=logsMapper.query(map);
            resultMap.put("logs",list);
            list=logsMapper.selectLogsAll();
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

    @Override
    public JSONArray kind(Integer kind) {
        return logsMapper.kind(kind);
    }

}
