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
 * @ClassName: SpecInfo
 * @Author: Smoadrareun
 * @Description: TODO 规格信息实体类
 */

@Table(name="spec_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecInfo implements Serializable {
    /**
     * 规格信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 规格信息id
     */
    @NotEmpty
    private String specId;

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

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)
    private Integer deleted;

    private static final long serialVersionUID = 1L;
}