package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Goods;
import com.hebeu.pojo.vo.GoodsVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: GoodsService
 * @Author: Smoadrareun
 * @Description: TODO 客户信息服务层接口
 */

public interface GoodsService extends IService<Goods> {

    GoodsVo getById(String id);

    List<GoodsVo> getList();

    PageInfo<GoodsVo> select(GoodsVo goodsVo);

    GoodsVo insert(GoodsVo goodsVo);

    GoodsVo insertSpecInfo(GoodsVo.SpecInfoVo specInfoVo);

    Boolean delete(Integer id);

    Boolean deleteSpecInfo(String id);

    GoodsVo update(GoodsVo goodsVo);

    GoodsVo updateSpecInfo(GoodsVo.SpecInfoVo specInfoVo);

}
