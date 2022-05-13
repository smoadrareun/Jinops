package com.hebeu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Client
 * @Author: Smoadrareun
 * @Description: TODO 客户信息实体类
 */

@Table(name="client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {
    /**
     * 客户信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 客户id
     */
    @NotEmpty
    private Integer cliId;

    /**
     * 客户账户名称
     */
    private String uname;

    /**
     * 客户账户密码
     */
    private String passwd;

    /**
     * 客户头像
     */
    private String pic;

    /**
     * 客户电话
     */
    private Long phone;

    /**
     * 客户昵称
     */
    private String nickname;

    /**
     * 客户邮箱
     */
    private String email;

    /**
     * 客户余额
     */
    private BigDecimal money;

    /**
     * 客户会员积分
     */
    private BigDecimal point;

    /**
     * 客户默认地址
     */
    private String defAdd;

    /**
     * 客户账户状态
     */
    private Integer status;

    /**
     * 客户注册时间
     */
    private Long regTime;

    /**
     * 上次登录时间
     */
    private Long logTime;

    /**
     * 上次登录IP
     */
    private String logIp;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}