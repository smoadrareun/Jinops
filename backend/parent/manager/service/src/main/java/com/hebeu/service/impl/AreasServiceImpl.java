package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.RedisUtil;
import com.hebeu.mapper.AreasMapper;
import com.hebeu.pojo.Areas;
import com.hebeu.service.AreasService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: AreasServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 行政区划信息服务层实现类
 */

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class AreasServiceImpl extends ServiceImpl<AreasMapper, Areas> implements AreasService {

    private AreasMapper areasMapper;

    private RedisUtil redisUtil;

    @Autowired
    public void setAreasMapper(AreasMapper areasMapper) {
        this.areasMapper = areasMapper;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public Areas getById(String id) {
        log.info("根据id查询行政区划信息开始，请求参数：{}", id);
        Areas areas = new Areas();
        try {
            if (redisUtil.hExists("areas", id)) {
                String str = String.valueOf(redisUtil.hGet("areas", id));
                Type type = new TypeToken<Areas>() {
                }.getType();
                areas = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", areas);
            } else {
                LambdaQueryWrapper<Areas> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Areas::getId, id);
                List<Areas> areasList = areasMapper.selectList(wrapper);
                if (areasList.size() > 0) {
                    areas = areasList.get(0);
                    log.info("根据id查询行政区划信息成功：{}", areas);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询行政区划信息失败：", e);
            return null;
        }
        return areas;
    }

    @Override
    public List<Areas> getList() {
        log.info("查询所有行政区划信息开始");
        List<Areas> areasList = new ArrayList<>();
        try {
            Set<Object> set = redisUtil.hKeys("areas");
            if (set != null && set.size() > 0) {
                for (Object obj : set) {
                    String str = String.valueOf(redisUtil.hGet("areas", String.valueOf(obj)));
                    Type type = new TypeToken<Areas>() {
                    }.getType();
                    areasList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Areas> wrapper = new LambdaQueryWrapper<>();
                List<Areas> list = areasMapper.selectList(wrapper);
                for (Areas areas : list) {
                    areasList.add(areas);
                    String json = new Gson().toJson(areas);
                    redisUtil.hPut("areas", String.valueOf(areas.getId()), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有行政区划信息成功：{}", areasList);
        } catch (Exception e) {
            log.error("查询所有行政区划信息失败：", e);
            return null;
        }
        return areasList;
    }

    @Override
    public List<Areas> select(Areas areas) {
        log.info("根据条件查询行政区划信息开始，请求参数：{}", areas);
        List<Areas> areasList = new ArrayList<>();
        try {
            LambdaQueryWrapper<Areas> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ObjectUtils.isNotEmpty(areas.getPid()), Areas::getPid, areas.getPid());
            wrapper.eq(ObjectUtils.isNotEmpty(areas.getExtId()), Areas::getExtId, areas.getExtId());
            wrapper.eq(ObjectUtils.isNotEmpty(areas.getDeep()), Areas::getDeep, areas.getDeep());
            wrapper.like(ObjectUtils.isNotEmpty(areas.getName()), Areas::getName, areas.getName());
            wrapper.like(ObjectUtils.isNotEmpty(areas.getFullname()), Areas::getFullname, areas.getFullname());
            wrapper.like(ObjectUtils.isNotEmpty(areas.getFullpath()), Areas::getFullpath, areas.getFullpath());
            wrapper.eq(ObjectUtils.isNotEmpty(areas.getPosition()), Areas::getPosition, areas.getPosition());
            wrapper.eq(ObjectUtils.isNotEmpty(areas.getPolygon()), Areas::getPolygon, areas.getPolygon());
            areasList = areasMapper.selectList(wrapper);
            log.info("根据条件查询行政区划信息成功：{}", areasList);
        } catch (Exception e) {
            log.error("根据条件查询行政区划信息失败：", e);
            return null;
        }
        return areasList;
    }

}
