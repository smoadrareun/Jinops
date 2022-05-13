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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Vendor
 * @Author: Smoadrareun
 * @Description: TODO 商户信息实体类
 */

@Table(name="vendor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vendor implements Serializable {
    /**
     * 商户信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商户id
     */
    @NotEmpty
    private Integer venId;

    /**
     * 商户账户名称
     */
    private String uname;

    /**
     * 商户账户密码
     */
    private String passwd;

    /**
     * 商户头像
     */
    private String pic;

    /**
     * 商户昵称
     */
    private String nickname;

    /**
     * 商户电话
     */
    private Long phone;

    /**
     * 商户邮箱
     */
    private String email;

    /**
     * 商户账户状态
     */
    private Integer status;

    /**
     * 商户注册时间
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