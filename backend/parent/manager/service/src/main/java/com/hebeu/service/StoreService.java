package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Store;
import com.hebeu.pojo.vo.StoreVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: StoreService
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息服务层接口
 */

public interface StoreService extends IService<Store> {

    StoreVo getById(Integer id);

    List<StoreVo> getList();

    PageInfo<StoreVo> select(StoreVo storeVo);

    StoreVo insert(StoreVo storeVo);

    StoreVo insertDistInfo(StoreVo.DistInfoVo distInfoVo);

    Boolean delete(Integer id);

    Boolean deleteDistInfo(String id);

    StoreVo update(StoreVo storeVo);

    StoreVo updateDistInfo(StoreVo.DistInfoVo distInfoVo);
}
