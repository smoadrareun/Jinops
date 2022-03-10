package com.hebeu.mapper;

import com.hebeu.model.GoodsModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: GoodsMapper
 * @Author: Smoadrareun
 * @Description: TODO 商品信息数据层接口
 */

@Repository
@Component
@Mapper
public interface GoodsMapper {

    List<GoodsModel> selectGoodsAll();

    GoodsModel selectGoodById(@Param("id") Integer id);

    List<GoodsModel> find(Map<String,Object> map);

    List<GoodsModel> search(Map<String,Object> map);

    void insert(Map<String,Object> map);

    void insertSpecInfo(Map<String,Object> map);

    void delete(@Param("id") Integer id);

    void deleteSpecInfo(@Param("id") Integer id);

    void update(Map<String,Object> map);

    void updateSpecInfo(Map<String,Object> map);

    List<GoodsModel> query(Map<String,Object> map);

}
