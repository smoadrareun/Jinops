package com.hebeu.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: Areas
 * @Author: Smoadrareun
 * @Description: TODO 行政区划信息实体类
 */

@Table(name="areas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Areas implements Serializable {
    /**
     * 主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 简id
     */
    private Integer pid;

    /**
     * 全id
     */
    private Long extId;

    /**
     * 行政区划层级
     */
    private String deep;

    /**
     * 行政区划名称
     */
    private String name;

    /**
     * 拼音缩写
     */
    private String pinyinPrefix;

    /**
     * 拼音全称
     */
    private String pinyin;

    /**
     * 行政区划全名
     */
    private String fullname;

    /**
     * 行政区划全路径
     */
    private String fullpath;

    /**
     * 地理位置
     */
    private String position;

    /**
     * 地理范围
     */
    private String polygon;

    private static final long serialVersionUID = 1L;
}