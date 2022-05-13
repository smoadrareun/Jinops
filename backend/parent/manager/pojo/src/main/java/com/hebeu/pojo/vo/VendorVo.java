package com.hebeu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: Vendor
 * @Author: Smoadrareun
 * @Description: TODO 商户信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorVo implements Serializable {

    private static final long serialVersionUID = 1L;

    //商户唯一标识符
    private Integer id;

    //商户账户名称
    private String uname;

    //商户账户密码
    private String passwd;

    //商户姓名
    private String name;

    //商户电话
    private Long phone;

    //商户头像
    private String pic;

    //商户管理员状态
    private Boolean admin;

}


