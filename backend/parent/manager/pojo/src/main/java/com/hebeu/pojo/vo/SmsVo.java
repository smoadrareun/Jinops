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
 * @Description: TODO 短信信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 手机号码
     */
    private Long phone;

    /**
     * 验证码长度
     */
    private Integer length;

    /**
     * 过期时间
     */
    private Integer minute;

    /**
     * 验证码
     */
    private String code;

}


