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
 * @ClassName: Goods
 * @Author: Smoadrareun
 * @Description: TODO 商品信息实体类
 */

@Table(name="goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable {
    /**
     * 商品信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 商品id
     */
    @NotEmpty
    private String gooId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品库存
     */
    private Integer num;

    /**
     * 商品销量
     */
    private Integer sold;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 商品分类
     */
    private String kind;

    /**
     * 商品介绍
     */
    private String info;

    /**
     * 商品状态
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