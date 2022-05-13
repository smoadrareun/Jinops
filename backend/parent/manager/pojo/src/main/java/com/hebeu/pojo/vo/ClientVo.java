package com.hebeu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * @ClassName: Client
 * @Author: Smoadrareun
 * @Description: TODO 客户信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户id
     */
    private Integer id;

    /**
     * 客户账户名称
     */
    private String uname;

    /**
     * 客户账户密码
     */
    private String passwd;

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
     * 客户头像
     */
    private String pic;

    /**
     * 地址信息
     */
    private List<AddrInfoVo> addrInfo;

    /**
     * 分页页数
     */
    private Integer pageNum;

    /**
     * 分页每页大小
     */
    private Integer pageSize;

    /**
     * 客户Token
     */
    private String token;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddrInfoVo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 地址信息id
         */
        private String id;

        /**
         * 客户id
         */
        private Integer cliId;

        /**
         * 联系人
         */
        private String name;

        /**
         * 电话号码
         */
        private Long phone;

        /**
         * 详细地址
         */
        private String address;

        /**
         * 邮政编码
         */
        private String postcode;

        /**
         * 经度度数
         */
        private BigDecimal longitude;

        /**
         * 纬度度数
         */
        private BigDecimal latitude;

    }

}



