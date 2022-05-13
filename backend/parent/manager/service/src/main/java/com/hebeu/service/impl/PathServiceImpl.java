package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.RedisUtil;
import com.hebeu.mapper.PathMapper;
import com.hebeu.pojo.Path;
import com.hebeu.service.PathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: PathServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 权限信息服务层实现类
 */

@Slf4j
@Service
public class PathServiceImpl extends ServiceImpl<PathMapper, Path> implements PathService {

    @Resource
    private PathMapper pathMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Path getById(Integer id) {
        log.info("根据id查询权限信息开始，请求参数：{}", id);
        Path path = new Path();
        try {
            if (redisUtil.hasKey("path" + id)) {
                String str = String.valueOf(redisUtil.get("path" + id));
                Type type = new TypeToken<Path>() {
                }.getType();
                path = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", path);
            } else {
                LambdaQueryWrapper<Path> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Path::getId, id);
                List<Path> list = pathMapper.selectList(wrapper);
                if (list.size() > 0) {
                    log.info("根据id查询权限信息成功：{}", list.get(0));
                }
            }
        } catch (Exception e) {
            log.error("根据id查询权限信息失败：", e);
            return null;
        }
        return path;
    }

    @Override
    public List<Path> getList() {
        log.info("查询所有权限信息开始");
        List<Path> pathList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("path*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<Path>() {
                    }.getType();
                    pathList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Path> wrapper = new LambdaQueryWrapper<>();
                List<Path> list = pathMapper.selectList(wrapper);
                for (Path path : list) {
                    pathList.add(path);
                    String json = new Gson().toJson(path);
                    redisUtil.set("path" + path.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有权限信息成功：{}", pathList);
        } catch (Exception e) {
            log.error("查询所有权限信息失败：", e);
            return null;
        }
        return pathList;
    }

    @Override
    public List<Path> select(Path path) {
        log.info("根据条件查询权限信息开始，请求参数：{}", path);
        PageInfo<Path> pageInfoVo = new PageInfo<>();
        try {
            LambdaQueryWrapper<Path> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ObjectUtils.isNotEmpty(path.getUrl()), Path::getUrl, path.getUrl());
            wrapper.like(ObjectUtils.isNotEmpty(path.getDesc()), Path::getDesc, path.getDesc());
            List<Path> list = pathMapper.selectList(wrapper);
            log.info("根据条件查询权限信息成功：{}", list);
        } catch (Exception e) {
            log.error("根据条件查询权限信息失败：", e);
            return null;
        }
        return list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Path insert(Path path) {
        log.info("添加权限信息开始，请求参数：{}", path);
        try {
            int count = pathMapper.insert(path);
            if (count > 0) {
                path = getById(path.getId());
                String str = new Gson().toJson(path);
                redisUtil.set("path" + path.getId(), str);
                log.info("向Redis缓存中添加数据成功");
                log.info("添加权限信息成功：{}",path);
            }
        } catch (Exception e) {
            log.error("添加权限信息失败：", e);
            return null;
        }
        return path;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除权限信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Path> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Path::getId, id);
            List<Path> list = pathMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = pathMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("path" + id)) {
                        redisUtil.delete("path" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除权限信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除权限信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Path update(Path path) {
        log.info("修改权限信息开始，请求参数：{}", path);
        Path paths = new Path();
        try {
            LambdaQueryWrapper<Path> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Path::getId, path.getId());
            List<Path> list = pathMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = pathMapper.update(path, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("path" + path.getId())) {
                        redisUtil.delete("path" + path.getId());
                    }
                    paths = getById(path.getId());
                    String str = new Gson().toJson(paths);
                    redisUtil.set("path" + paths.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改权限信息成功：{}",path);
                }
            }
        } catch (Exception e) {
            log.error("修改权限信息失败：", e);
            return null;
        }
        return paths;
    }

}
