package com.hebeu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @ClassName: Client
 * @Author: Smoadrareun
 * @Description: TODO 客户信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientModel implements Serializable {

    private static final long serialVersionUID = 1L;
    //客户信息唯一标识符
    private Integer id;

    //客户账户名称
    private String uname;

    //客户账户密码
    private String passwd;

    //客户余额
    private BigDecimal money;

    //客户会员积分
    private Integer point;

    //客户地址
    private List<AddInfo> addInfo;

    //客户默认地址
    private String defAdd;

    //客户头像
    private String pic;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        //地址信息唯一标识符
        private String id;

        //客户id
        private Integer cliId;

        //联系人
        private String name;

        //电话号码
        private Long phone;

        //详细地址
        private String address;

        //邮政编码
        private String postcode;

        //经度度数
        private BigDecimal long_;

        //纬度度数
        private BigDecimal lat_;

    }

}



