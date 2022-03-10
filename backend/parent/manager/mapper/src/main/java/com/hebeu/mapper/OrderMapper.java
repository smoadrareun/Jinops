package com.hebeu.mapper;

import com.hebeu.model.OrderModel;
import com.hebeu.model.OrderModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: OrderMapper
 * @Author: Smoadrareun
 * @Description: TODO 交易信息数据层接口
 */

@Repository
@Component
@Mapper
public interface OrderMapper {

    List<OrderModel> selectOrdersAll();

    OrderModel selectOrderById(@Param("id") String id);

    List<OrderModel> find(Map<String,Object> map);

    List<OrderModel> search(Map<String,Object> map);

    void insert(Map<String,Object> map);

    void insertComInfo(Map<String,Object> map);

    void delete(@Param("id") String id);

    void deleteComInfo(@Param("id") String id);

    void update(Map<String,Object> map);

    void updateComInfo(Map<String,Object> map);

    List<OrderModel> query(Map<String,Object> map);

}