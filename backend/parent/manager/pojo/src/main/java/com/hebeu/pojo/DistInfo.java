package com.hebeu.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalTime;
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
 * @ClassName: DistInfo
 * @Author: Smoadrareun
 * @Description: TODO 配送信息实体类
 */

@Table(name="dist_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DistInfo implements Serializable {
    /**
     * 配送信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 配送信息id
     */
    @NotEmpty
    private String distId;

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

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}