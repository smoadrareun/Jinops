package com.hebeu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hebeu.pojo.Role;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RoleService
 * @Author: Smoadrareun
 * @Description: TODO 角色信息服务层接口
 */

public interface RoleService extends IService<Role> {

    Role getById(Integer id);

    List<Role> getList();

    List<Role> select(Role role);

    Role insert(Role role);

    Boolean delete(Integer id);

    Role update(Role role);

}
