package com.hebeu.mapper;

import com.hebeu.model.ClientModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientMapper
 * @Author: Smoadrareun
 * @Description: TODO 客户信息数据层接口
 */

@Repository
@Mapper
public interface ClientMapper {

    Integer login(Map<String,Object> map);

    List<ClientModel> selectClientAll();

    ClientModel selectClientById(@Param("id") Integer id);

    List<ClientModel> find(Map<String,Object> map);

    List<ClientModel> search(Map<String,Object> map);

    void insert(Map<String,Object> map);

    void insertAddInfo(Map<String,Object> map);

    void delete(@Param("id") Integer id);

    void deleteAddInfo(@Param("id") Integer id);

    void update(Map<String,Object> map);

    void updateAddInfo(Map<String,Object> map);

}