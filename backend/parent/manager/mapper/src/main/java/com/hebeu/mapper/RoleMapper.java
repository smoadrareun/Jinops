package com.hebeu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hebeu.pojo.Role;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: RoleMapper
 * @Author: Smoadrareun
 * @Description: TODO 角色信息持久层接口
 */

@Repository
public interface RoleMapper extends BaseMapper<Role> {
}