package com.hebeu.mapper;

import com.hebeu.model.ClientModel;
import com.hebeu.model.ClientModelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClientMapper {
    long countByExample(ClientModelExample example);

    int deleteByExample(ClientModelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ClientModel record);

    int insertSelective(ClientModel record);

    List<ClientModel> selectByExample(ClientModelExample example);

    ClientModel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ClientModel record, @Param("example") ClientModelExample example);

    int updateByExample(@Param("record") ClientModel record, @Param("example") ClientModelExample example);

    int updateByPrimaryKeySelective(ClientModel record);

    int updateByPrimaryKey(ClientModel record);
}