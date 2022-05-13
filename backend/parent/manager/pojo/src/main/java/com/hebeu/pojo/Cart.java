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
 * @ClassName: Cart
 * @Author: Smoadrareun
 * @Description: TODO 购物车实体类
 */

@Table(name="cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    /**
     * 购物车信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 购物车信息id
     */
    @NotEmpty
    private String cartId;

    /**
     * 客户id
     */
    private Integer cliId;

    /**
     * 商品id
     */
    private String gooId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 规格选项
     */
    private String specName;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 购买总价
     */
    private BigDecimal price;

    /**
     * 购物车状态
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