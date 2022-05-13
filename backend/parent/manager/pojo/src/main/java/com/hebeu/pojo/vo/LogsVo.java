package com.hebeu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: LogModel
 * @Author: Smoadrareun
 * @Description: TODO 日志信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogsVo {
    private static final long serialVersionUID = 1L;

    //日志信息唯一标识符
    private String id;

    //创建时间
    private Long createDate;

    //类名称
    private String className;

    //方法名称
    private String methodName;

    //请求参数
    private String requestArgs;

    //运行用时
    private Long executionTime;

    //访问IP
    private String requestIp;

    //操作类型
    private String logType;

}
