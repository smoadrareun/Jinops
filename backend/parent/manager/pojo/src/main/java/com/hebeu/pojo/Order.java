package com.hebeu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Order
 * @Author: Smoadrareun
 * @Description: TODO 交易信息实体类
 */

@Table(name="order")
@TableName(value ="`order`")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {
    /**
     * 交易信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 交易信息id
     */
    @NotEmpty
    private String ordId;

    /**
     * 客户id
     */
    private Integer cliId;

    /**
     * 客户名称
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
     * 交易日期
     */
    private Long date;

    /**
     * 交易总额
     */
    private BigDecimal price;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 订单状态
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