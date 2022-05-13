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
import com.hebeu.mapper.DistInfoMapper;
import com.hebeu.mapper.StoreMapper;
import com.hebeu.pojo.DistInfo;
import com.hebeu.pojo.Store;
import com.hebeu.pojo.vo.StoreVo;
import com.hebeu.service.StoreService;
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
 * @ClassName: StoreServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息服务层实现类
 */

@Slf4j
@Service
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements StoreService {

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private DistInfoMapper distInfoMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Store,StoreVo> PageInfo<StoreVo> pageInfoPoToVo(PageInfo<Store> pageInfo){
        Page<StoreVo> page=new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static StoreVo storePoToVo(Store store, List<DistInfo> distInfoList) {
        if (store == null) {
            return null;
        }
        StoreVo storeVo = new StoreVo();
        BeanUtils.copyProperties(store, storeVo);
        storeVo.setId(store.getStoId());
        storeVo.setDistInfo(DistListToVo(distInfoList));
        return storeVo;
    }

    public static Store storeVoToPo(StoreVo storeVo) {
        if (storeVo == null) {
            return null;
        }
        Store store = new Store();
        BeanUtils.copyProperties(storeVo, store);
        store.setId(null);
        store.setStoId(storeVo.getId());
        return store;
    }

    public static DistInfo DistVoToPo(StoreVo.DistInfoVo distInfoVo) {
        if (distInfoVo == null) {
            return null;
        }
        DistInfo distInfo = new DistInfo();
        BeanUtils.copyProperties(distInfoVo, distInfo);
        distInfo.setId(null);
        return distInfo;
    }

    public static List<StoreVo.DistInfoVo> DistListToVo(List<DistInfo> distInfoList) {
        if (distInfoList == null) {
            return null;
        }
        List<StoreVo.DistInfoVo> distInfoVoList = new ArrayList<>();
        for (DistInfo distInfo : distInfoList) {
            StoreVo.DistInfoVo distInfoVo = new StoreVo.DistInfoVo();
            BeanUtils.copyProperties(distInfo, distInfoVo);
            distInfoVo.setId(distInfo.getDistId());
            distInfoVoList.add(distInfoVo);
        }
        return distInfoVoList;
    }

    //根据id查询店铺信息
    @Override
    public StoreVo getById(Integer id) {
        log.info("根据id查询店铺信息开始，请求参数：{}", id);
        StoreVo storeVo = new StoreVo();
        try {
            if (redisUtil.hasKey("store" + id)) {
                String str = String.valueOf(redisUtil.get("store" + id));
                Type type = new TypeToken<StoreVo>() {
                }.getType();
                storeVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", storeVo);
            } else {
                LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Store::getStoId, id);
                List<Store> list = storeMapper.selectList(wrapper);
                if (list.size() > 0) {
                    LambdaQueryWrapper<DistInfo> distInfoWrapper = new LambdaQueryWrapper<>();
                    distInfoWrapper.eq(DistInfo::getStoId, list.get(0).getStoId());
                    List<DistInfo> distInfoList = distInfoMapper.selectList(distInfoWrapper);
                    storeVo = storePoToVo(list.get(0), distInfoList);
                    log.info("根据id查询店铺信息成功：{}", storeVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询店铺信息失败：", e);
            return null;
        }
        return storeVo;
    }

    //查询所有店铺信息
    @Override
    public List<StoreVo> getList() {
        log.info("查询所有店铺信息开始");
        List<StoreVo> storeVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("store*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<StoreVo>() {
                    }.getType();
                    storeVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
                List<Store> list = storeMapper.selectList(wrapper);
                for (Store store : list) {
                    LambdaQueryWrapper<DistInfo> distInfoWrapper = new LambdaQueryWrapper<>();
                    distInfoWrapper.eq(DistInfo::getStoId, store.getStoId());
                    List<DistInfo> distInfoList = distInfoMapper.selectList(distInfoWrapper);
                    StoreVo storeVo = storePoToVo(store, distInfoList);
                    storeVoList.add(storeVo);
                    String json = new Gson().toJson(storeVo);
                    redisUtil.set("store" + storeVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有店铺信息成功：{}", storeVoList);
        } catch (Exception e) {
            log.error("查询所有店铺信息失败：", e);
            return null;
        }
        return storeVoList;
    }

    //根据条件查询店铺信息
    @Override
    public PageInfo<StoreVo> select(StoreVo storeVo) {
        log.info("根据条件查询店铺信息开始，请求参数：{}", storeVo);
        PageInfo<StoreVo> pageInfoVo=new PageInfo<>();
        try {
            LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(ObjectUtils.isNotEmpty(storeVo.getName()), Store::getName, storeVo.getName());
            wrapper.like(ObjectUtils.isNotEmpty(storeVo.getAddress()), Store::getAddress, storeVo.getAddress());
            wrapper.eq(ObjectUtils.isNotEmpty(storeVo.getLongitude()), Store::getLongitude, storeVo.getLongitude());
            wrapper.eq(ObjectUtils.isNotEmpty(storeVo.getLatitude()), Store::getLatitude, storeVo.getLatitude());
            PageHelper.startPage(ObjectUtils.isNotEmpty(storeVo.getPageNum()) ? storeVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(storeVo.getPageSize()) ? storeVo.getPageSize() : 10);
            List<Store> list = storeMapper.selectList(wrapper);
            PageInfo<Store> pageInfo=new PageInfo<>(list);
            pageInfoVo=pageInfoPoToVo(pageInfo);
            for (Store store : list) {
                LambdaQueryWrapper<DistInfo> distInfoWrapper = new LambdaQueryWrapper<>();
                distInfoWrapper.eq(DistInfo::getStoId, store.getStoId());
                List<DistInfo> distInfoList = distInfoMapper.selectList(distInfoWrapper);
                pageInfoVo.getList().add(storePoToVo(store,distInfoList));
            }
            log.info("根据条件查询店铺信息成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询店铺信息失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    //添加店铺信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreVo insert(StoreVo storeVo) {
        log.info("添加店铺信息开始，请求参数：{}", storeVo);
        StoreVo storeVos = new StoreVo();
        try {
            Store store = storeVoToPo(storeVo);
            store.setStoId(IDUtil.getID());
            LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Store::getStoId, store.getStoId());
            List<Store> list = storeMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = storeMapper.insert(store);
                if (count > 0) {
                    storeVos = getById(store.getStoId());
                    String str = new Gson().toJson(storeVos);
                    redisUtil.set("cart" + storeVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加店铺信息成功：{}",storeVos);
                }
            }
        } catch (Exception e) {
            log.error("添加店铺信息失败：", e);
            return null;
        }
        return storeVos;
    }

    //添加店铺配送信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreVo insertDistInfo(StoreVo.DistInfoVo distInfoVo) {
        log.info("添加店铺配送信息开始，请求参数：{}", distInfoVo);
        StoreVo storeVo = new StoreVo();
        try {
            LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Store::getStoId, distInfoVo.getStoId());
            List<Store> list = storeMapper.selectList(wrapper);
            if (list.size() > 0) {
                DistInfo distInfo = DistVoToPo(distInfoVo);
                distInfo.setDistId(IDUtil.getShortUUID());
                LambdaQueryWrapper<DistInfo> distInfoWrapper = new LambdaQueryWrapper<>();
                distInfoWrapper.eq(DistInfo::getDistId, distInfo.getDistId());
                List<DistInfo> distInfoList = distInfoMapper.selectList(distInfoWrapper);
                if (distInfoList.size() == 0) {
                    int count = distInfoMapper.insert(distInfo);
                    if (count > 0) {
                        if (redisUtil.hasKey("store" + list.get(0).getStoId())) {
                            redisUtil.delete("store" + list.get(0).getStoId());
                        }
                        storeVo = getById(list.get(0).getStoId());
                        String str = new Gson().toJson(storeVo);
                        redisUtil.set("store" + list.get(0).getStoId(), str);
                        log.info("从Redis缓存中添加数据成功");
                        log.info("添加店铺配送信息成功：{}",storeVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("添加店铺配送信息失败：", e);
            return null;
        }
        return storeVo;
    }

    //删除店铺信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除店铺信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Store::getStoId, id);
            List<Store> list = storeMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = storeMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("store" + id)) {
                        redisUtil.delete("store" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除店铺信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除店铺信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //删除店铺配送信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteDistInfo(String id) {
        log.info("删除店铺配送信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<DistInfo> distInfoWrapper = new LambdaQueryWrapper<>();
            distInfoWrapper.eq(DistInfo::getDistId, id);
            List<DistInfo> distInfoList = distInfoMapper.selectList(distInfoWrapper);
            if (distInfoList.size() > 0) {
                LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Store::getStoId, distInfoList.get(0).getStoId());
                List<Store> list = storeMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count=distInfoMapper.delete(distInfoWrapper);
                    if(count>0){
                        if (redisUtil.hasKey("store" + list.get(0).getStoId())) {
                            redisUtil.delete("store" + list.get(0).getStoId());
                        }
                        String str = new Gson().toJson(getById(list.get(0).getStoId()));
                        redisUtil.set("store" + list.get(0).getStoId(), str);
                        aBoolean = true;
                        log.info("从Redis缓存中删除数据成功");
                        log.info("删除店铺配送信息成功");
                    }
                }
            }
        } catch (Exception e) {
            log.error("删除店铺配送信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    //修改店铺信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreVo update(StoreVo storeVo) {
        log.info("修改店铺信息开始，请求参数：{}", storeVo);
        StoreVo storeVos = new StoreVo();
        try {
            Store store = storeVoToPo(storeVo);
            LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Store::getStoId, store.getStoId());
            List<Store> list = storeMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = storeMapper.update(store, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("store" + store.getStoId())) {
                        redisUtil.delete("store" + store.getStoId());
                    }
                    storeVos = getById(store.getStoId());
                    String str = new Gson().toJson(storeVos);
                    redisUtil.set("store" + storeVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改店铺信息成功：{}",storeVos);
                }
            }
        } catch (Exception e) {
            log.error("修改店铺信息失败：", e);
            return null;
        }
        return storeVos;
    }

    //修改店铺配送信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public StoreVo updateDistInfo(StoreVo.DistInfoVo distInfoVo) {
        log.info("修改店铺配送信息开始，请求参数：{}", distInfoVo);
        StoreVo storeVo = new StoreVo();
        try {
            DistInfo distInfo = DistVoToPo(distInfoVo);
            LambdaQueryWrapper<DistInfo> distInfoWrapper = new LambdaQueryWrapper<>();
            distInfoWrapper.eq(DistInfo::getDistId, distInfo.getDistId());
            List<DistInfo> distInfoList = distInfoMapper.selectList(distInfoWrapper);
            if (distInfoList.size() > 0) {
                LambdaQueryWrapper<Store> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Store::getStoId, distInfoList.get(0).getStoId());
                List<Store> list = storeMapper.selectList(wrapper);
                if (list.size() > 0) {
                    int count = distInfoMapper.update(distInfo, distInfoWrapper);
                    if (count > 0) {
                        if (redisUtil.hasKey("store" + list.get(0).getStoId())) {
                            redisUtil.delete("store" + list.get(0).getStoId());
                        }
                        storeVo = getById(list.get(0).getStoId());
                        String str = new Gson().toJson(storeVo);
                        redisUtil.set("store" + list.get(0).getStoId(), str);
                        log.info("从Redis缓存中更新数据成功");
                        log.info("修改店铺配送信息成功：{}",storeVo);
                    }
                }
            }
        } catch (Exception e) {
            log.error("修改店铺配送信息失败：", e);
            return null;
        }
        return storeVo;
    }

}
