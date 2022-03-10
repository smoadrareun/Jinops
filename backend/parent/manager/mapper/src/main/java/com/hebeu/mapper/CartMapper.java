package com.hebeu.mapper;

import com.hebeu.model.CartModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: CartMapper
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息数据层接口
 */

@Repository
@Component
@Mapper
public interface CartMapper {

    List<CartModel> selectCartAll();

    CartModel selectCartById(@Param("id") String id);

    List<CartModel> find(Map<String,Object> map);

    List<CartModel> search(Map<String,Object> map);

    void insert(Map<String,Object> map);

    void delete(@Param("id") String id);

    void update(Map<String,Object> map);

    List<CartModel> query(Map<String,Object> map);

}
