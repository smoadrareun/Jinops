package com.hebeu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Cart
 * @Author: Smoadrareun
 * @Description: TODO 购物车信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 购物车信息唯一标识符
     */
    private String id;

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
     * 最低价
     */
    private BigDecimal minPrice;

    /**
     * 最高价
     */
    private BigDecimal maxPrice;

    /**
     * 购物车状态
     */
    private Integer status;

    /**
     * 商品图片
     */
    private String pic;

    /**
     * 分页页数
     */
    private Integer pageNum;

    /**
     * 分页每页大小
     */
    private Integer pageSize;

    /**
     * 排序方式
     */
    private Integer sort;

}
