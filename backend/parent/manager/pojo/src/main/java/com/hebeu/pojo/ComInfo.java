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
 * @ClassName: ComInfo
 * @Author: Smoadrareun
 * @Description: TODO 订单信息实体类
 */

@Table(name="com_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComInfo implements Serializable {
    /**
     * 订单详情主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 订单详情id
     */
    @NotEmpty
    private String comId;

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

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}