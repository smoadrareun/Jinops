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
 * @ClassName: Store
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息实体类
 */

@Table(name="store")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Store implements Serializable {
    /**
     * 店铺信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 店铺id
     */
    @NotEmpty
    private Integer stoId;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺电话
     */
    private Long phone;

    /**
     * 店铺地址
     */
    private String address;

    /**
     * 店铺头像
     */
    private String pic;

    /**
     * 店铺营业额
     */
    private BigDecimal income;

    /**
     * 积分抵扣
     */
    private BigDecimal point;

    /**
     * 经度度数
     */
    private BigDecimal longitude;

    /**
     * 纬度度数
     */
    private BigDecimal latitude;

    /**
     * 店铺状态
     */
    private Integer status;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}