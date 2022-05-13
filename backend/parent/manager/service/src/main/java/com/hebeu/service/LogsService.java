package com.hebeu.service;

import com.alibaba.fastjson.JSONArray;
import com.hebeu.model.LogsModel;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: LogsService
 * @Author: Smoadrareun
 * @Description: TODO 日志信息服务层接口
 */

public interface LogsService {

    List<LogsModel> getList();

    LogsModel getById(String id);

    List<LogsModel> find(Map<String,Object> map);

    List<LogsModel> search(Map<String,Object> map);

    Map<String,Object> query(Map<String,Object> map);

    JSONArray kind(Integer kind);
}
