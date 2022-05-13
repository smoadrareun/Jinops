package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.RedisUtil;
import com.hebeu.mapper.RoleMapper;
import com.hebeu.pojo.Role;
import com.hebeu.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RoleServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 角色信息服务层实现类
 */

@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public Role getById(Integer id) {
        log.info("根据id查询角色信息开始，请求参数：{}", id);
        Role role = new Role();
        try {
            if (redisUtil.hasKey("role" + id)) {
                String str = String.valueOf(redisUtil.get("role" + id));
                Type type = new TypeToken<Role>() {
                }.getType();
                role = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", role);
            } else {
                LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Role::getId, id);
                List<Role> list = roleMapper.selectList(wrapper);
                if (list.size() > 0) {
                    log.info("根据id查询角色信息成功：{}", list.get(0));
                }
            }
        } catch (Exception e) {
            log.error("根据id查询角色信息失败：", e);
            return null;
        }
        return role;
    }

    @Override
    public List<Role> getList() {
        log.info("查询所有角色信息开始");
        List<Role> roleList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("role*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<Role>() {
                    }.getType();
                    roleList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
                List<Role> list = roleMapper.selectList(wrapper);
                for (Role role : list) {
                    roleList.add(role);
                    String json = new Gson().toJson(role);
                    redisUtil.set("role" + role.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有角色信息成功：{}", roleList);
        } catch (Exception e) {
            log.error("查询所有角色信息失败：", e);
            return null;
        }
        return roleList;
    }

    @Override
    public List<Role> select(Role role) {
        log.info("根据条件查询角色信息开始，请求参数：{}", role);
        PageInfo<Role> pageInfoVo = new PageInfo<>();
        try {
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ObjectUtils.isNotEmpty(role.getName()), Role::getName, role.getName());
            wrapper.like(ObjectUtils.isNotEmpty(role.getDesc()), Role::getDesc, role.getDesc());
            List<Role> list = roleMapper.selectList(wrapper);
            log.info("根据条件查询角色信息成功：{}", list);
        } catch (Exception e) {
            log.error("根据条件查询角色信息失败：", e);
            return null;
        }
        return list();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role insert(Role role) {
        log.info("添加角色信息开始，请求参数：{}", role);
        try {
            int count = roleMapper.insert(role);
            if (count > 0) {
                role = getById(role.getId());
                String str = new Gson().toJson(role);
                redisUtil.set("role" + role.getId(), str);
                log.info("向Redis缓存中添加数据成功");
                log.info("添加角色信息成功：{}",role);
            }
        } catch (Exception e) {
            log.error("添加角色信息失败：", e);
            return null;
        }
        return role;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer id) {
        log.info("删除角色信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Role::getId, id);
            List<Role> list = roleMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = roleMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("role" + id)) {
                        redisUtil.delete("role" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除角色信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除角色信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role update(Role role) {
        log.info("修改角色信息开始，请求参数：{}", role);
        Role roles = new Role();
        try {
            LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Role::getId, role.getId());
            List<Role> list = roleMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = roleMapper.update(role, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("role" + role.getId())) {
                        redisUtil.delete("role" + role.getId());
                    }
                    roles = getById(role.getId());
                    String str = new Gson().toJson(roles);
                    redisUtil.set("role" + roles.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改角色信息成功：{}",role);
                }
            }
        } catch (Exception e) {
            log.error("修改角色信息失败：", e);
            return null;
        }
        return roles;
    }

}
