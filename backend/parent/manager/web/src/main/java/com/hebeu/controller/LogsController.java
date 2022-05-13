package com.hebeu.controller;

import com.github.pagehelper.PageInfo;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.LogsVo;
import com.hebeu.service.LogsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: LogsController
 * @Author: Smoadrareun
 * @Description: TODO 日志信息控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/logs")
public class LogsController {

    @Resource
    private LogsService logsService;

    public ResponseBody<Object> resp = new ResponseBody<>();

    //根据日志id查询数据
    @RequestMapping("/getById/{id}")
    public ResponseBody<Object> getById(@PathVariable("id") String id) {
        LogsVo logsVo = logsService.getById(id);
        if (logsVo == null) {
            return resp.failure(-1, "根据id查询日志信息失败");
        } else if (logsVo.getId() == null) {
            return resp.failure(-404, "根据id查询日志信息未找到数据");
        } else {
            return resp.success("根据id查询日志信息成功", logsVo);
        }
    }

    //查询所有日志数据
    @RequestMapping("/getList")
    public ResponseBody<Object> getList() {
        List<LogsVo> list = logsService.getList();
        if (list == null) {
            return resp.failure(-1, "查询所有日志信息失败");
        } else if (list.size() == 0) {
            return resp.failure(-404, "查询所有日志信息未找到数据");
        } else {
            return resp.success("查询所有日志信息成功", new PageInfo<>(list));
        }
    }

    //根据条件查询日志数据
    @RequestMapping("/select")
    public ResponseBody<Object> select(@RequestBody LogsVo logsVo) {
        PageInfo<LogsVo> pageInfo = logsService.select(logsVo);
        if (pageInfo == null) {
            return resp.failure(-1, "根据条件查询日志信息失败");
        } else if (pageInfo.getList() == null) {
            return resp.failure(-404, "根据条件查询日志信息未找到数据");
        } else {
            return resp.success("根据条件查询日志信息成功", pageInfo);
        }
    }

    //添加日志数据
    @RequestMapping("/insert")
    public ResponseBody<Object> insert(@RequestBody LogsVo logsVo) {
        LogsVo logsVos = logsService.insert(logsVo);
        if (logsVos == null || logsVos.getId() == null) {
            return resp.failure(-1, "添加日志信息失败");
        } else {
            return resp.success("添加日志信息成功", logsVos);
        }
    }

    //根据日志id删除数据
    @RequestMapping("/delete/{id}")
    public ResponseBody<Object> delete(@PathVariable("id") String id) {
        Boolean aBoolean = logsService.delete(id);
        if (aBoolean == null) {
            return resp.failure(-1, "删除日志信息失败");
        } else if (!aBoolean) {
            return resp.failure(-404, "需删除的日志信息不存在");
        } else {
            return resp.success("删除日志信息成功", null);
        }
    }

    //修改日志数据
    @RequestMapping("/update")
    public ResponseBody<Object> update(@RequestBody LogsVo logsVo) {
        LogsVo logsVos = logsService.update(logsVo);
        if (logsVos == null) {
            return resp.failure(-1, "修改日志信息失败");
        } else if (logsVos.getId() == null) {
            return resp.failure(-404, "需修改的日志信息不存在");
        } else {
            return resp.success("修改日志信息成功", null);
        }
    }

}
