package com.hebeu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hebeu.common.DateUtil;
import com.hebeu.common.IDUtil;
import com.hebeu.common.RedisUtil;
import com.hebeu.mapper.LogsMapper;
import com.hebeu.pojo.Logs;
import com.hebeu.pojo.Order;
import com.hebeu.pojo.vo.LogsVo;
import com.hebeu.service.LogsService;
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
 * @ClassName: LogsServiceImpl
 * @Author: Smoadrareun
 * @Description: TODO 日志信息服务层实现类
 */

@Slf4j
@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService {

    @Resource
    private LogsMapper logsMapper;

    @Resource
    private RedisUtil redisUtil;

    public static <Logs,LogsVo> PageInfo<LogsVo> pageInfoPoToVo(PageInfo<Logs> pageInfo){
        Page<LogsVo> page=new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize());
        page.setTotal(pageInfo.getTotal());
        return new PageInfo<>(page);
    }

    public static LogsVo logsPoToVo(Logs logs) {
        if (logs == null) {
            return null;
        }
        LogsVo logsVo = new LogsVo();
        BeanUtils.copyProperties(logs, logsVo);
        logsVo.setId(logs.getLogId());
        logsVo.setCreTime(DateUtil.TimeMillToDate(logs.getCreTime(), "yyyy-MM-dd HH:mm:ss"));
        return logsVo;
    }

    public static Logs logsVoToPo(LogsVo logsVo) {
        if (logsVo == null) {
            return null;
        }
        Logs logs = new Logs();
        BeanUtils.copyProperties(logsVo, logs);
        logs.setId(null);
        logs.setLogId(logsVo.getId());
        logs.setCreTime(DateUtil.DateToTimeMill(logsVo.getCreTime(),"yyyy-MM-dd HH:mm:ss"));
        return logs;
    }

    @Override
    public LogsVo getById(String id) {
        log.info("根据id查询日志信息开始，请求参数：{}", id);
        LogsVo logsVo = new LogsVo();
        try {
            if (redisUtil.hasKey("logs" + id)) {
                String str = String.valueOf(redisUtil.get("logs" + id));
                Type type = new TypeToken<LogsVo>() {
                }.getType();
                logsVo = new Gson().fromJson(str, type);
                log.info("从Redis缓存中取得数据成功：{}", logsVo);
            } else {
                LambdaQueryWrapper<Logs> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Logs::getLogId, id);
                List<Logs> list = logsMapper.selectList(wrapper);
                if (list.size() > 0) {
                    logsVo = logsPoToVo(list.get(0));
                    log.info("根据id查询日志信息成功：{}", logsVo);
                }
            }
        } catch (Exception e) {
            log.error("根据id查询日志信息失败：", e);
            return null;
        }
        return logsVo;
    }

    @Override
    public List<LogsVo> getList() {
        log.info("查询所有日志信息开始");
        List<LogsVo> logsVoList = new ArrayList<>();
        try {
            Set<String> set = redisUtil.keys("logs*");
            if (set != null && set.size() > 0) {
                for (String s : set) {
                    String str = redisUtil.get(s);
                    Type type = new TypeToken<LogsVo>() {
                    }.getType();
                    logsVoList.add(new Gson().fromJson(str, type));
                }
                log.info("从Redis缓存中取得数据成功");
            } else {
                LambdaQueryWrapper<Logs> wrapper = new LambdaQueryWrapper<>();
                List<Logs> list = logsMapper.selectList(wrapper);
                for (Logs logs : list) {
                    LogsVo logsVo = logsPoToVo(logs);
                    logsVoList.add(logsVo);
                    String json = new Gson().toJson(logsVo);
                    redisUtil.set("logs" + logsVo.getId(), json);
                }
                log.info("将数据存入到Redis缓存成功");
            }
            log.info("查询所有日志信息成功：{}", logsVoList);
        } catch (Exception e) {
            log.error("查询所有日志信息失败：", e);
            return null;
        }
        return logsVoList;
    }

    @Override
    public PageInfo<LogsVo> select(LogsVo logsVo) {
        log.info("根据条件查询日志信息开始，请求参数：{}", logsVo);
        PageInfo<LogsVo> pageInfoVo = new PageInfo<>();
        try {
            LambdaQueryWrapper<Logs> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(ObjectUtils.isNotEmpty(logsVo.getOperator()), Logs::getOperator, logsVo.getOperator());
            wrapper.gt(ObjectUtils.isNotEmpty(logsVo.getStartTime()), Logs::getCreTime,
                    DateUtil.DateToTimeMill(logsVo.getStartTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.lt(ObjectUtils.isNotEmpty(logsVo.getEndTime()), Logs::getCreTime,
                    DateUtil.DateToTimeMill(logsVo.getEndTime(), "yyyy-MM-dd HH:mm:ss"));
            wrapper.orderByAsc(ObjectUtils.isNotEmpty(logsVo.getSort())
                    && logsVo.getSort() == 1, Logs::getCreTime);
            wrapper.orderByDesc(ObjectUtils.isNotEmpty(logsVo.getSort())
                    && logsVo.getSort() == 2, Logs::getCreTime);
            PageHelper.startPage(ObjectUtils.isNotEmpty(logsVo.getPageNum()) ? logsVo.getPageNum() : 1,
                    ObjectUtils.isNotEmpty(logsVo.getPageSize()) ? logsVo.getPageSize() : 10);
            List<Logs> list = logsMapper.selectList(wrapper);
            PageInfo<Logs> pageInfo=new PageInfo<>(list);
            pageInfoVo=pageInfoPoToVo(pageInfo);
            for (Logs logs:list){
                pageInfoVo.getList().add(logsPoToVo(logs));
            }
            log.info("根据条件查询日志信息成功：{}", pageInfoVo);
        } catch (Exception e) {
            log.error("根据条件查询日志信息失败：", e);
            return null;
        }
        return pageInfoVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LogsVo insert(LogsVo logsVo) {
        log.info("添加日志信息开始，请求参数：{}", logsVo);
        LogsVo logsVos = new LogsVo();
        try {
            Logs logs = logsVoToPo(logsVo);
            logs.setLogId(IDUtil.getShortUUID());
            LambdaQueryWrapper<Logs> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Logs::getLogId, logs.getLogId());
            List<Logs> list = logsMapper.selectList(wrapper);
            if (list.size() == 0) {
                int count = logsMapper.insert(logs);
                if (count > 0) {
                    logsVos = getById(logs.getLogId());
                    String str = new Gson().toJson(logsVos);
                    redisUtil.set("logs" + logsVos.getId(), str);
                    log.info("向Redis缓存中添加数据成功");
                    log.info("添加日志信息成功：{}",logsVos);
                }
            }
        } catch (Exception e) {
            log.error("添加日志信息失败：", e);
            return null;
        }
        return logsVos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(String id) {
        log.info("删除日志信息开始，请求参数：{}", id);
        boolean aBoolean = false;
        try {
            LambdaQueryWrapper<Logs> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Logs::getLogId, id);
            List<Logs> list = logsMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = logsMapper.delete(wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("logs" + id)) {
                        redisUtil.delete("logs" + id);
                        log.info("从Redis缓存中删除数据成功");
                    }
                    aBoolean = true;
                    log.info("删除日志信息成功");
                }
            }
        } catch (Exception e) {
            log.error("删除日志信息失败：", e);
            return null;
        }
        return aBoolean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LogsVo update(LogsVo logsVo) {
        log.info("修改日志信息开始，请求参数：{}", logsVo);
        LogsVo logsVos = new LogsVo();
        try {
            Logs logs = logsVoToPo(logsVo);
            LambdaQueryWrapper<Logs> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Logs::getLogId, logs.getLogId());
            List<Logs> list = logsMapper.selectList(wrapper);
            if (list.size() > 0) {
                int count = logsMapper.update(logs, wrapper);
                if (count > 0) {
                    if (redisUtil.hasKey("logs" + logs.getLogId())) {
                        redisUtil.delete("logs" + logs.getLogId());
                    }
                    logsVos = getById(logs.getLogId());
                    String str = new Gson().toJson(logsVos);
                    redisUtil.set("logs" + logsVos.getId(), str);
                    log.info("从Redis缓存中更新数据成功");
                    log.info("修改日志信息成功：{}",logsVos);
                }
            }
        } catch (Exception e) {
            log.error("修改日志信息失败：", e);
            return null;
        }
        return logsVos;
    }

}
