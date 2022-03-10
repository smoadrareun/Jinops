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
 * @ClassName: Order
 * @Author: Smoadrareun
 * @Description: TODO 交易信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //交易信息唯一标识符
    private String id;

    //客户id
    private Integer cliId;

    //客户名称
    private String name;

    //电话号码
    private Long phone;

    //详细地址
    private String address;

    //交易日期
    private Long date;

    //交易总额
    private BigDecimal price;

    //订单详情
    private List<ComInfo> comInfo;

    //订单备注
    private String remark;

    //订单状态
    private Boolean status;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ComInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        //订单详情唯一标识符
        private String id;

        //交易信息id
        private String ordId;

        //商品名称
        private String name;

        //规格选项
        private String specName;

        //购买数量
        private Integer num;

        //购买总价
        private BigDecimal price;

    }
}
