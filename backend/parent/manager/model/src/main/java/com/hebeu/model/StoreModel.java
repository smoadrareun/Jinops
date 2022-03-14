package com.hebeu.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: Store
 * @Author: Smoadrareun
 * @Description: TODO 店铺信息实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreModel implements Serializable {

    private static final long serialVersionUID = 1L;

    //店铺信息唯一标识符
    private Integer id;

    //店铺名称
    private String name;

    //店铺电话
    private Long phone;

    //店铺地址
    private String address;

    //店铺营业额
    private BigDecimal income;

    //积分抵扣
    private BigDecimal point;

    //店铺配送信息
    private List<DistInfo> distInfo;

    //经度度数
    private BigDecimal long_;

    //纬度度数
    private BigDecimal lat_;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class DistInfo implements Serializable {

        private static final long serialVersionUID = 1L;

        //配送信息唯一标识符
        private String id;

        //店铺id
        private Integer stoId;

        //配送状态
        private Integer status;

        //起送时间
        private Date beginTime;

        //停送时间
        private Date endTime;

        //配送范围
        private Integer range;

        //起送价
        private BigDecimal startPri;

        //配送费
        private BigDecimal deliCost;

        //打包费
        private BigDecimal packFee;

    }
}