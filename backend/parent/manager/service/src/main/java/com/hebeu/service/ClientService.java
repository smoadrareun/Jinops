package com.hebeu.service;

import com.hebeu.model.ClientModel;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientService
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层接口
 */

public interface ClientService {

    Map<String,Object> login(Map<String,Object> map);

    List<ClientModel> getList();

    ClientModel getById(Integer id);

    List<ClientModel> find(Map<String,Object> map);

    List<ClientModel> search(Map<String,Object> map);

    Boolean insert(Map<String,Object> map);

    Boolean insertAddInfo(Map<String,Object> map);

    Boolean delete(Integer id);

    Boolean deleteAddInfo(Integer id);

    Boolean update(Map<String,Object> map);

    Boolean updateAddInfo(Map<String,Object> map);
}