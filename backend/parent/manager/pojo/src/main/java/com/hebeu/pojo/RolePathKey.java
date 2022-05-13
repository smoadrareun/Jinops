package com.hebeu.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RolePathKey
 * @Author: Smoadrareun
 * @Description: TODO 角色-权限实体类
 */

@Table(name="role_path")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePathKey implements Serializable {
    /**
     * 角色id
     */
    @Id
    @TableId(type = IdType.AUTO)
    @GeneratedValue(generator = "JDBC")
    private Integer rid;

    /**
     * 权限id
     */
    @Id
    @GeneratedValue
    private Integer pid;

    private static final long serialVersionUID = 1L;
}