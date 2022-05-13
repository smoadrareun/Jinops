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
 * @ClassName: AddrInfo
 * @Author: Smoadrareun
 * @Description: TODO 地址信息实体类
 */

@Table(name="addr_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddrInfo implements Serializable {
    /**
     * 地址信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 地址信息id
     */
    @NotEmpty
    private String addrId;

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

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}