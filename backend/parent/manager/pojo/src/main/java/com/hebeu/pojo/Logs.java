package com.hebeu.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Logs
 * @Author: Smoadrareun
 * @Description: TODO 日志信息实体类
 */

@Table(name="logs")
@Data
public class Logs implements Serializable {
    /**
     * 日志信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 日志信息id
     */
    @NotEmpty
    private String logId;

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
    private Long creTime;

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
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}