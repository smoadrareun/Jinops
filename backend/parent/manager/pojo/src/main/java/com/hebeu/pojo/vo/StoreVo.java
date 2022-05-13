package com.hebeu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: Store
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 店铺id
     */
    private Integer id;

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
     * 配送信息
     */
    private List<DistInfoVo> distInfo;

    /**
     * 分页页数
     */
    private Integer pageNum;

    /**
     * 分页每页大小
     */
    private Integer pageSize;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DistInfoVo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 配送信息id
         */
        private String id;

        /**
         * 店铺id
         */
        private Integer stoId;

        /**
         * 配送状态
         */
        private Integer status;

        /**
         * 起送时间
         */
        private LocalTime begTime;

        /**
         * 停送时间
         */
        private LocalTime endTime;

        /**
         * 配送范围
         */
        private Integer range;

        /**
         * 起送价
         */
        private BigDecimal startPri;

        /**
         * 配送费
         */
        private BigDecimal deliCost;

        /**
         * 打包费
         */
        private BigDecimal packFee;

    }
}