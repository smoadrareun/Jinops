package com.hebeu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: Goods
 * @Author: Smoadrareun
 * @Description: TODO 商品信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GoodsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //商品信息唯一标识符
    private Integer id;

    //商品名称
    private String name;

    //商品价格
    private BigDecimal price;

    //商品库存
    private Integer num;

    //商品销量
    private Integer sold;

    //商品图片
    private String pic;

    //商品分类
    private String kind;

    //商品介绍
    private String info;

    //商品是否下架
    private Boolean off;

    //商品规格
    private List<SpecInfo> specInfo;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class SpecInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        //规格信息唯一标识符
        private String id;

        //商品id
        private Integer gooId;

        //规格名称
        private String type;

        //规格选项
        private String name;

        //规格价格
        private BigDecimal price;

        //规格库存
        private Integer num;

    }
}

