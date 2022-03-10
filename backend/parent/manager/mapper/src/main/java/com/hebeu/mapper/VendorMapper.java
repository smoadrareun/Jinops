package com.hebeu.mapper;

import com.hebeu.model.VendorModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: VendorMapper
 * @Author: Smoadrareun
 * @Description: TODO 商户信息数据层接口
 */


@Repository
@Mapper
public interface VendorMapper {

    Integer login(Map<String,Object> map);

    List<VendorModel> selectVendorAll();

    VendorModel selectVendorById(@Param("id") Integer id);

    List<VendorModel> find(Map<String,Object> map);

    List<VendorModel> search(Map<String,Object> map);

    void insert(Map<String,Object> map);

    void delete(@Param("id") Integer id);

    void update(Map<String,Object> map);

}