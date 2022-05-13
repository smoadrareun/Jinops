package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.*;
import com.hebeu.mapper.PathMapper;
import com.hebeu.mapper.RoleMapper;
import com.hebeu.mapper.RolePathMapper;
import com.hebeu.pojo.*;
import com.hebeu.pojo.vo.RolePathVo;
import com.hebeu.pojo.vo.RolePathVo;
import com.hebeu.service.RolePathService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RolePathServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 角色-权限服务层实现类
 */

@Slf4j
@Service
public class RolePathServiceImpl extends ServiceImpl<RolePathMapper, RolePathKey> implements RolePathService {

    @Resource
    private RolePathMapper rolePathMapper;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private PathMapper pathMapper;

    @Resource
    private RedisUtil redisUtil;

    public static RolePathVo rolePathPoToVo(Role role, List<Path> pathList) {
        if (role == null) {
            return null;
        }
        RolePathVo rolePathVo = new RolePathVo();
        BeanUtils.copyProperties(role, rolePathVo);
        rolePathVo.setPath(PathListToVo(pathList));
        return rolePathVo;
    }

    public static Role rolePathVoToRole(RolePathVo rolePathVo) {
        if (rolePathVo == null) {
            return null;
        }
        Role role = new Role();
        BeanUtils.copyProperties(rolePathVo, role);
        return role;
    }

    public static Path rolePathVoToPath(RolePathVo.PathVo pathVo) {
        if (pathVo == null) {
            return null;
        }
        Path path = new Path();
        BeanUtils.copyProperties(pathVo, path);
        return path;
    }

    public static List<RolePathVo.PathVo> PathListToVo(List<Path> pathList) {
        if (pathList == null) {
            return null;
        }
        List<RolePathVo.PathVo> pathVoList = new ArrayList<>();
        for (Path path : pathList) {
            RolePathVo.PathVo pathVo = new RolePathVo.PathVo();
            BeanUtils.copyProperties(path, pathVo);
            pathVoList.add(pathVo);
        }
        return pathVoList;
    }

    //根据角色id查询角色-权限
    @Override
    public RolePathVo getByRoleId(Integer id) {
        log.info("根据角色id查询角色-权限开始，请求参数：{}", id);
        RolePathVo rolePathVo = new RolePathVo();
        try {
            if (redisUtil.hasKey("rolePath" + id)) {
                String str = String.valueOf(redisUtil.get("rolePath" + id));
                Type type = new TypeToken<RolePathVo>() {
                }.getType();
                rolePathVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", rolePathVo);
            } else {
                Role role = roleMapper.selectById(id);
                if (role != null) {
                    LambdaQueryWrapper<RolePathKey> rolePathWrapper = new LambdaQueryWrapper<>();
                    rolePathWrapper.eq(RolePathKey::getRid, id);
                    List<RolePathKey> rolePathList = rolePathMapper.selectList(rolePathWrapper);
                    List<Path> pathList = new ArrayList<>();
                    for (RolePathKey rolePathKey : rolePathList) {
                        LambdaQueryWrapper<Path> pathWrapper = new LambdaQueryWrapper<>();
                        pathWrapper.eq(Path::getId, rolePathKey.getPid());
                        pathList = pathMapper.selectList(pathWrapper);
                    }
                    rolePathVo = rolePathPoToVo(role, pathList);
                    log.info("根据角色id查询角色-权限成功：{}", rolePathVo);
                }
            }
        } catch (Exception e) {
            log.error("根据角色id查询角色-权限失败：", e);
            return null;
        }
        return rolePathVo;
    }

    //根据权限id查询角色-权限
    @Override
    public List<RolePathVo> getByPathId(Integer id) {
        log.info("根据权限id查询角色-权限开始，请求参数：{}", id);
        List<RolePathVo> rolePathVoList = new ArrayList<>();
        try {
            Path path = pathMapper.selectById(id);
            if (path != null) {
                LambdaQueryWrapper<RolePathKey> rolePathWrapper = new LambdaQueryWrapper<>();
                rolePathWrapper.eq(RolePathKey::getPid, id);
                List<RolePathKey> rolePathList = rolePathMapper.selectList(rolePathWrapper);
                for (RolePathKey rolePathKey : rolePathList) {
                    rolePathVoList.add(getByRoleId(rolePathKey.getRid()));
                }
                log.info("根据权限id查询角色-权限成功：{}", rolePathVoList);
            }
        } catch (Exception e) {
            log.error("根据权限id查询角色-权限失败：", e);
            return null;
        }
        return rolePathVoList;
    }

    //查询所有角色-权限
    @Override
    public List<RolePathVo> getList() {
        log.info("查询所有角色-权限开始");
        List<RolePathVo> rolePathVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("rolePath*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<RolePathVo>() {
                    }.getType();
                    rolePathVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Role> roleWrapper = new LambdaQueryWrapper<>();
                List<Role> roleList = roleMapper.selectList(roleWrapper);
                for (Role role : roleList) {
                    LambdaQueryWrapper<RolePathKey> rolePathWrapper = new LambdaQueryWrapper<>();
                    rolePathWrapper.eq(RolePathKey::getRid, role.getId());
                    List<RolePathKey> rolePathList = rolePathMapper.selectList(rolePathWrapper);
                    List<Path> pathList = new ArrayList<>();
                    for (RolePathKey rolePathKey : rolePathList) {
                        LambdaQueryWrapper<Path> pathWrapper = new LambdaQueryWrapper<>();
                        pathWrapper.eq(Path::getId, rolePathKey.getPid());
                        pathList = pathMapper.selectList(pathWrapper);
                    }
                    RolePathVo rolePathVo = rolePathPoToVo(roleList.get(0), pathList);
                    rolePathVoList.add(rolePathVo);
                    String json = new Gson().toJson(rolePathVo);
                    redisUtil.set("rolePath" + rolePathVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有角色-权限成功：{}", rolePathVoList);
        } catch (Exception e) {
            log.error("查询所有角色-权限失败：", e);
            return null;
        }
        return rolePathVoList;
    }

    //添加角色-权限
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RolePathVo insert(Integer rid, Integer pid) {
        log.info("添加角色-权限开始，请求参数：{},{}", rid, pid);
        RolePathVo rolePathVo = new RolePathVo();
        try {
            LambdaQueryWrapper<RolePathKey> rolePathWrapper = new LambdaQueryWrapper<>();
            rolePathWrapper.eq(RolePathKey::getRid, rid);
            rolePathWrapper.eq(RolePathKey::getPid, pid);
            List<RolePathKey> rolePathList = rolePathMapper.selectList(rolePathWrapper);
            if (rolePathList.size() == 0) {
                RolePathKey rolePathKey = new RolePathKey();
                rolePathKey.setRid(rid);
                rolePathKey.setPid(pid);
                int count = rolePathMapper.insert(rolePathKey);
                if (count > 0) {
                    rolePathVo = getByRoleId(rid);
                    String str = new Gson().toJson(rolePathVo);
                    redisUtil.set("rolePath" + rolePathVo.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加角色-权限成功：{}",rolePathVo);
                }

            }
        } catch (Exception e) {
            log.error("添加角色-权限失败：", e);
            throw e;
        }
        return rolePathVo;
    }

    //删除角色-权限
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Integer rid, Integer pid) {
        log.info("删除角色-权限开始，请求参数：{},{}", rid, pid);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<RolePathKey> rolePathWrapper = new LambdaQueryWrapper<>();
            rolePathWrapper.eq(RolePathKey::getRid, rid);
            rolePathWrapper.eq(RolePathKey::getPid, pid);
            List<RolePathKey> rolePathList = rolePathMapper.selectList(rolePathWrapper);
            if (rolePathList.size() > 0) {
                int count = rolePathMapper.delete(rolePathWrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("rolePath" + rid)) {
                        redisUtil.delete("rolePath" + rid);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除角色-权限成功");
                }
            }
        } catch (Exception e) {
            log.error("删除角色-权限失败：", e);
            throw e;
        }
        return aBoolean;
    }

}
