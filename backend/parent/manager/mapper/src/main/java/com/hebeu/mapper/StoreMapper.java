package com.hebeu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hebeu.pojo.Store;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: StoreMapper
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息持久层接口
 */

@Repository
public interface StoreMapper extends BaseMapper<Store> {
}