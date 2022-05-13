package com.hebeu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: Vendor
 * @Author: Smoadrareun
 * @Description: TODO 商户信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商户id
     */
    private Integer id;

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
    private String regTime;

    /**
     * 上次登录时间
     */
    private String logTime;

    /**
     * 上次登录IP
     */
    private String logIp;

    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 分页页数
     */
    private Integer pageNum;

    /**
     * 分页每页大小
     */
    private Integer pageSize;

    /**
     * 商户Token
     */
    private String token;

}


