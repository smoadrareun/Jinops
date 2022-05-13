//package com.hebeu.security;
//
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.hebeu.mapper.ClientMapper;
//import com.hebeu.mapper.RoleMapper;
//import com.hebeu.pojo.Client;
//import com.hebeu.pojo.Role;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: UserDetailsServiceImpl
// * @Author: Smoadrareun
// * @Description: TODO 用户权限服务层实现类
// */
//
//public class UserSecurityService implements UserDetailsService {
//
//    @Resource
//    private ClientMapper clientMapper;	//SysUserMapper : sys_user表的Dao对象
//    @Resource
//    private RoleMapper roleMapper;	//sys_role表的Dao对象
//
//    @Override
//    //如果查无此人，抛出UsernameNotFoundException异常即可，框架会自己处理这个异常。
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if(username == null){
//            throw new UsernameNotFoundException("name is null!");
//        }
//
//        LambdaQueryWrapper<Client> wrapper=new LambdaQueryWrapper<>();
//        wrapper.eq(Client::getUname,username);
//        List<Client> list =clientMapper.selectList(wrapper);	//查询用户的记录
//        if(list.size()==0){
//            throw new UsernameNotFoundException("the account: " + username + " not found!");
//        }
//
//        Role role = roleMapper.selectById(list.get(0).getRid());	//查询用户所持有的Role信息
//
//        if(role == null){
//            throw new UsernameNotFoundException("no role records found!");
//        }
//
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        //对于RBAC，role名称前必须加上"ROLE_"
//        //框架是根据前缀是否有ROLE_来判断这是角色信息还是权限信息的。
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
//
//        //将查询到的信息封装到UserDetail的实现类：User里面。
//        User user = new User(username, list.get(0).getPasswd(), list.get(0).getDeleted() == 0,
//                list.get(0).getStatus() == 1, list.get(0).getStatus() == 2,
//                list.get(0).getStatus() == 3, authorities);
//
//        return user;
//    }
//}
