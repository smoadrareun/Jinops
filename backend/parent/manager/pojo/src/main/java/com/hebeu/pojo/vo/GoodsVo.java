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
 * @ClassName: Goods
 * @Author: Smoadrareun
 * @Description: TODO 商品信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private String id;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品价格
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
     * 规格信息
     */
    private List<SpecInfoVo> specInfo;

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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpecInfoVo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 规格信息id
         */
        private String id;

        /**
         * 商品id
         */
        private String gooId;

        /**
         * 规格名称
         */
        private String type;

        /**
         * 规格选项
         */
        private String name;

        /**
         * 规格价格
         */
        private BigDecimal price;

        /**
         * 规格库存
         */
        private Integer num;

    }
}

