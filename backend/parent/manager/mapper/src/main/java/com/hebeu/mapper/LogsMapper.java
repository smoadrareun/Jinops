package com.hebeu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hebeu.pojo.Logs;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: LogsMapper
 * @Author: Smoadrareun
 * @Description: TODO 日志信息持久层接口
 */

@Repository
public interface LogsMapper extends BaseMapper<Logs> {
}