//package com.hebeu.security;
//
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Iterator;
//
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: CustomizeAccessDecisionManager
// * @Author: Smoadrareun
// * @Description: TODO
// */
//
//@Component
//public class CustomizeAccessDecisionManager implements AccessDecisionManager {
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        //configAttributes  访问需要的角色
//        //authentication.getAuthorities()  用户的权限
//        //两个其实都是集合，我们进行双层循环遍历一下，看有没有匹配上的就行了
//        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
//        while (iterator.hasNext()) {
//            ConfigAttribute ca = iterator.next();
//            //当前请求需要的权限，因为我们做的是RBAC，所以拿到的权限名，前面要加ROLE_前缀
//            String needRole = "ROLE_" + ca.getAttribute();
//
//            //当前用户所具有的权限
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(needRole)) {
//                    //匹配上了就结束
//                    return;
//                }
//            }
//        }
//
//        //没有匹配项，抛出AccessDeniedException异常
//        throw new AccessDeniedException("权限不足!");
//    }
//
//    @Override
//    //启用
//    public boolean supports(ConfigAttribute attribute) {
//        return true;
//    }
//
//    @Override
//    //启用
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}