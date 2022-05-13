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
 * @ClassName: Order
 * @Author: Smoadrareun
 * @Description: TODO 交易信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 交易信息id
     */
    private String id;

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
    private String date;

    /**
     * 起始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

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
     * 订单详情
     */
    private List<ComInfoVo> comInfo;

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
    public static class ComInfoVo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 订单详情id
         */
        private String id;

        /**
         * 交易信息id
         */
        private String ordId;

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

    }
}
