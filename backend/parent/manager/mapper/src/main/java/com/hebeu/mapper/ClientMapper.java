package com.hebeu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hebeu.pojo.Client;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: ClientMapper
 * @Author: Smoadrareun
 * @Description: TODO 客户信息持久层接口
 */

@Repository
public interface ClientMapper extends BaseMapper<Client> {
}