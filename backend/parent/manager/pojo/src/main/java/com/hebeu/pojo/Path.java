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
 * @ClassName: Path
 * @Author: Smoadrareun
 * @Description: TODO 权限信息实体类
 */

@Table(name="path")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Path implements Serializable {
    /**
     * 权限信息主键
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 请求路径地址
     */
    private String url;

    /**
     * 请求路径描述
     */
    @TableField(value = "`desc`")
    private String desc;

    private static final long serialVersionUID = 1L;
}