package com.hebeu.pojo.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RolePathVo
 * @Author: Smoadrareun
 * @Description: TODO 角色-权限控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePathVo implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 角色信息主键
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String desc;

    /**
     * 地址信息
     */
    private List<PathVo> path;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PathVo implements Serializable {

        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * 权限信息主键
         */
        private Integer id;

        /**
         * 请求路径地址
         */
        private String url;

        /**
         * 请求路径描述
         */
        private String desc;

    }
}
