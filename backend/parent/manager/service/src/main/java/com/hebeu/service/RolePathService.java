package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hebeu.pojo.RolePathKey;
import com.hebeu.pojo.vo.RolePathVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RolePathService
 * @Author: Smoadrareun
 * @Description: TODO 角色-权限服务层接口
 */

public interface RolePathService extends IService<RolePathKey> {

    RolePathVo getByRoleId(Integer id);

    List<RolePathVo> getByPathId(Integer id);

    List<RolePathVo> getList();

    RolePathVo insert(Integer rid,Integer pid);

    Boolean delete(Integer rid,Integer pid);

}
