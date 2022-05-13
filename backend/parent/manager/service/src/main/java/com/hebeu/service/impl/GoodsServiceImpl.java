package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.*;
import com.hebeu.mapper.SpecInfoMapper;
import com.hebeu.mapper.GoodsMapper;
import com.hebeu.pojo.SpecInfo;
import com.hebeu.pojo.Goods;
import com.hebeu.pojo.vo.GoodsVo;
import com.hebeu.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: GoodsServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 商品信息服务层实现类
 */

@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private SpecInfoMapper specInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Goods,GoodsVo> PageInfo<GoodsVo> pageInfoPoToVo(PageInfo<Goods> pageInfo){
        Page<GoodsVo> page=new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static GoodsVo goodsPoToVo(Goods goods, List<SpecInfo> specInfoList) {
        if (goods == null) {
            return null;
        }
        GoodsVo goodsVo = new GoodsVo();
        BeanUtils.copyProperties(goods, goodsVo);
        goodsVo.setId(goods.getGooId());
        goodsVo.setSpecInfo(SpecListToVo(specInfoList));
        return goodsVo;
    }

    public static Goods goodsVoToPo(GoodsVo goodsVo) {
        if (goodsVo == null) {
            return null;
        }
        Goods goods = new Goods();
        BeanUtils.copyProperties(goodsVo, goods);
        goods.setId(null);
        goods.setGooId(goodsVo.getId());
        return goods;
    }

    public static SpecInfo SpecVoToPo(GoodsVo.SpecInfoVo specInfoVo) {
        if (specInfoVo == null) {
            return null;
        }
        SpecInfo specInfo = new SpecInfo();
        BeanUtils.copyProperties(specInfoVo, specInfo);
        specInfo.setId(null);
        return specInfo;
    }

    public static List<GoodsVo.SpecInfoVo> SpecListToVo(List<SpecInfo> specInfoList) {
        if (specInfoList == null) {
            return null;
        }
        List<GoodsVo.SpecInfoVo> specInfoVoList = new ArrayList<>();
        for (SpecInfo specInfo : specInfoList) {
            GoodsVo.SpecInfoVo specInfoVo = new GoodsVo.SpecInfoVo();
            BeanUtils.copyProperties(specInfo, specInfoVo);
            specInfoVo.setId(specInfo.getSpecId());
            specInfoVoList.add(specInfoVo);
        }
        return specInfoVoList;
    }

    //根据id查询商品信息
    @Override
    public GoodsVo getById(String id) {
        log.info("根据id查询商品信息开始，请求参数：{}", id);
        GoodsVo goodsVo = new GoodsVo();
        try {
            if (redisUtil.hasKey("goods" + id)) {
                String str = String.valueOf(redisUtil.get("goods" + id));
                Type type = new TypeToken<GoodsVo>() {
                }.getType();
                goodsVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", goodsVo);
            } else {
                LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Goods::getGooId, id);
                List<Goods> list = goodsMapper.selectList(wrapper);
                if (list.size() > 0) {
                    LambdaQueryWrapper<SpecInfo> specInfoWrapper = new LambdaQueryWrapper<>();
                    specInfoWrapper.eq(SpecInfo::getGooId, list.get(0).getGooId());
                    List<SpecInfo> specInfoList = specInfoMapper.selectList(specInfoWrapper);
                    goodsVo = goodsPoToVo(list.get(0), specInfoList);
                    log.info("根据id查询商品信息成功：{}", goodsVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询商品信息失败：", e);
            return null;
        }
        return goodsVo;
    }

    //查询所有商品信息
    @Override
    public List<GoodsVo> getList() {
        log.info("查询所有商品信息开始");
        List<GoodsVo> goodsVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("goods*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<GoodsVo>() {
                    }.getType();
                    goodsVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
                List<Goods> list = goodsMapper.selectList(wrapper);
                for (Goods goods : list) {
                    LambdaQueryWrapper<SpecInfo> specInfoWrapper = new LambdaQueryWrapper<>();
                    specInfoWrapper.eq(SpecInfo::getGooId, goods.getGooId());
                    List<SpecInfo> specInfoList = specInfoMapper.selectList(specInfoWrapper);
                    GoodsVo goodsVo = goodsPoToVo(goods, specInfoList);
                    goodsVoList.add(goodsVo);
                    String json = new Gson().toJson(goodsVo);
                    redisUtil.set("goods" + goodsVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有商品信息成功：{}", goodsVoList);
        } catch (Exception e) {
            log.error("查询所有商品信息失败：", e);
            return null;
        }
        return goodsVoList;
    }

    //根据条件查询商品信息
    @Override
    public PageInfo<GoodsVo> select(GoodsVo goodsVo) {
        log.info("根据条件查询商品信息开始，请求参数：{}", goodsVo);
        PageInfo<GoodsVo> pageInfoVo=new PageInfo<>();
        try {
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(ObjectUtils.isNotEmpty(goodsVo.getName()), Goods::getName, goodsVo.getName());
            wrapper.ge(ObjectUtils.isNotEmpty(goodsVo.getMinPrice()), Goods::getPrice, goodsVo.getMinPrice());
            wrapper.le(ObjectUtils.isNotEmpty(goodsVo.getMaxPrice()), Goods::getPrice, goodsVo.getMaxPrice());
            wrapper.gt(ObjectUtils.isNotEmpty(goodsVo.getNum()),Goods::getNum,goodsVo.getNum());
            wrapper.orderByAsc(ObjectUtils.isNotEmpty(goodsVo.getSort())
                    && goodsVo.getSort() == 1, Goods::getPrice);
            wrapper.orderByDesc(ObjectUtils.isNotEmpty(goodsVo.getSort())
                    && goodsVo.getSort() == 2, Goods::getPrice);
            wrapper.orderByAsc(ObjectUtils.isNotEmpty(goodsVo.getSort())
                    && goodsVo.getSort() == 3, Goods::getSold);
            wrapper.orderByDesc(ObjectUtils.isNotEmpty(goodsVo.getSort())
                    && goodsVo.getSort() == 4, Goods::getSold);
            wrapper.eq(ObjectUtils.isNotEmpty(goodsVo.getStatus()), Goods::getStatus, goodsVo.getStatus());
            PageHelper.startPage(ObjectUtils.isNotEmpty(goodsVo.getPageNum()) ? goodsVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(goodsVo.getPageSize()) ? goodsVo.getPageSize() : 10);
            List<Goods> list = goodsMapper.selectList(wrapper);
            PageInfo<Goods> pageInfo=new PageInfo<>(list);
            pageInfoVo=pageInfoPoToVo(pageInfo);
            for (Goods goods : list) {
                LambdaQueryWrapper<SpecInfo> specInfoWrapper = new LambdaQueryWrapper<>();
                specInfoWrapper.eq(SpecInfo::getGooId, goods.getGooId());
                List<SpecInfo> specInfoList = specInfoMapper.selectList(specInfoWrapper);
                pageInfoVo.getList().add(goodsPoToVo(goods,specInfoList));
            }
            log.info("根据条件查询商品信息成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询商品信息失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    //添加商品信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsVo insert(GoodsVo goodsVo) {
        log.info("添加商品信息开始，请求参数：{}", goodsVo);
        GoodsVo goodsVos = new GoodsVo();
        try {
            Goods goods = goodsVoToPo(goodsVo);
            goods.setGooId(IDUtil.getShortUUID());
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Goods::getGooId, goods.getGooId());
            List<Goods> list = goodsMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = goodsMapper.insert(goods);
                if (count > 0) {
                    goodsVos = getById(goods.getGooId());
                    String str = new Gson().toJson(goodsVos);
                    redisUtil.set("cart" + goodsVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加商品信息成功：{}",goodsVos);
                }
            }
        } catch (Exception e) {
            log.error("添加商品信息失败：", e);
            return null;
        }
        return goodsVos;
    }

    //添加商品规格信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsVo insertSpecInfo(GoodsVo.SpecInfoVo specInfoVo) {
        log.info("添加商品规格信息开始，请求参数：{}", specInfoVo);
        GoodsVo goodsVo = new GoodsVo();
        try {
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Goods::getGooId, specInfoVo.getGooId());
            List<Goods> list = goodsMapper.selectList(wrapper);
            if (list.size() > 0) {
                SpecInfo specInfo = SpecVoToPo(specInfoVo);
                specInfo.setSpecId(IDUtil.getShortUUID());
                LambdaQueryWrapper<SpecInfo> specInfoWrapper = new LambdaQueryWrapper<>();
                specInfoWrapper.eq(SpecInfo::getSpecId, specInfo.getSpecId());
                List<SpecInfo> specInfoList = specInfoMapper.selectList(specInfoWrapper);
                if (specInfoList.size() == 0) {
                    int count = specInfoMapper.insert(specInfo);
                    if (count > 0) {
                        if (redisUtil.hasKey("goods" + list.get(0).getGooId())) {
                            redisUtil.delete("goods" + list.get(0).getGooId());
                        }
                        goodsVo = getById(list.get(0).getGooId());
                        String str = new Gson().toJson(goodsVo);
                        redisUtil.set("goods" + list.get(0).getGooId(), str);
                        log.info("从Redis缓存中添加数据成功");
                        log.info("添加商品规格信息成功：{}",goodsVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("添加商品规格信息失败：", e);
            return null;
        }
        return goodsVo;
    }

    //删除商品信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除商品信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Goods::getGooId, id);
            List<Goods> list = goodsMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = goodsMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("goods" + id)) {
                        redisUtil.delete("goods" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除商品信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除商品信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //删除商品规格信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteSpecInfo(String id) {
        log.info("删除商品规格信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<SpecInfo> specInfoWrapper = new LambdaQueryWrapper<>();
            specInfoWrapper.eq(SpecInfo::getSpecId, id);
            List<SpecInfo> specInfoList = specInfoMapper.selectList(specInfoWrapper);
            if (specInfoList.size() > 0) {
                LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Goods::getGooId, specInfoList.get(0).getGooId());
                List<Goods> list = goodsMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count=specInfoMapper.delete(specInfoWrapper);
                    if(count>0){
                        if (redisUtil.hasKey("goods" + list.get(0).getGooId())) {
                            redisUtil.delete("goods" + list.get(0).getGooId());
                        }
                        String str = new Gson().toJson(getById(list.get(0).getGooId()));
                        redisUtil.set("goods" + list.get(0).getGooId(), str);
                        aBoolean = true;
                        log.info("从Redis缓存中删除数据成功");
                        log.info("删除商品规格信息成功");
                    }
                }
            }
        } catch (Exception e) {
            log.error("删除商品规格信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //修改商品信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsVo update(GoodsVo goodsVo) {
        log.info("修改商品信息开始，请求参数：{}", goodsVo);
        GoodsVo goodsVos = new GoodsVo();
        try {
            Goods goods = goodsVoToPo(goodsVo);
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Goods::getGooId, goods.getGooId());
            List<Goods> list = goodsMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = goodsMapper.update(goods, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("goods" + goods.getGooId())) {
                        redisUtil.delete("goods" + goods.getGooId());
                    }
                    goodsVos = getById(goods.getGooId());
                    String str = new Gson().toJson(goodsVos);
                    redisUtil.set("goods" + goodsVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改商品信息成功：{}",goodsVos);
                }
            }
        } catch (Exception e) {
            log.error("修改商品信息失败：", e);
            return null;
        }
        return goodsVos;
    }

    //修改商品规格信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsVo updateSpecInfo(GoodsVo.SpecInfoVo specInfoVo) {
        log.info("修改商品规格信息开始，请求参数：{}", specInfoVo);
        GoodsVo goodsVo = new GoodsVo();
        try {
            SpecInfo specInfo = SpecVoToPo(specInfoVo);
            LambdaQueryWrapper<SpecInfo> specInfoWrapper = new LambdaQueryWrapper<>();
            specInfoWrapper.eq(SpecInfo::getSpecId, specInfo.getSpecId());
            List<SpecInfo> specInfoList = specInfoMapper.selectList(specInfoWrapper);
            if (specInfoList.size() > 0) {
                LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Goods::getGooId, specInfoList.get(0).getGooId());
                List<Goods> list = goodsMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count = specInfoMapper.update(specInfo, specInfoWrapper);
                    if (count > 0) {
                        if (redisUtil.hasKey("goods" + list.get(0).getGooId())) {
                            redisUtil.delete("goods" + list.get(0).getGooId());
                        }
                        goodsVo = getById(list.get(0).getGooId());
                        String str = new Gson().toJson(goodsVo);
                        redisUtil.set("goods" + list.get(0).getGooId(), str);
                        log.info("从Redis缓存中更新数据成功");
                        log.info("修改商品规格信息成功：{}",goodsVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("修改商品规格信息失败：", e);
            return null;
        }
        return goodsVo;
    }

}
