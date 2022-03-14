package com.hebeu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Cart
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //购物车信息唯一标识符
    private String id;

    //客户id
    private Integer cliId;

    //商品id
    private Integer gooId;

    //商品名称
    private String name;

    //商品图片
    private String pic;

    //规格选项
    private String specName;

    //购买数量
    private Integer num;

    //购买总价
    private BigDecimal price;

}
