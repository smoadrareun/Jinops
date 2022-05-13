package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.hebeu.pojo.Logs;
import com.hebeu.pojo.vo.LogsVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: LogsService
 * @Author: Smoadrareun
 * @Description: TODO 日志信息服务层接口
 */

public interface LogsService extends IService<Logs> {

    LogsVo getById(String id);

    List<LogsVo> getList();

    PageInfo<LogsVo> select(LogsVo cartVo);

    LogsVo insert(LogsVo cartVo);

    Boolean delete(String id);

    LogsVo update(LogsVo cartVo);

}
