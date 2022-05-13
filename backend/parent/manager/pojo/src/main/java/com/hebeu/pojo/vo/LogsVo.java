package com.hebeu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: LogModel
 * @Author: Smoadrareun
 * @Description: TODO 日志信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志信息id
     */
    private String id;

    /**
     * 操作用户
     */
    private String operator;

    /**
     * 操作类型
     */
    private String logType;

    /**
     * 创建时间
     */
    private String creTime;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 请求参数
     */
    private String reqArgs;

    /**
     * 返回结果
     */
    private String result;

    /**
     * 运行用时
     */
    private Long runTime;

    /**
     * 访问IP
     */
    private String reqIp;

    /**
     * 分页页数
     */
    private Integer pageNum;

    /**
     * 分页每页大小
     */
    private Integer pageSize;

    /**
     * 排序方式
     */
    private Integer sort;

}
