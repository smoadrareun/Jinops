//package com.hebeu.security;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: CustomizeFilterInvocationSecurityMetadataSource
// * @Author: Smoadrareun
// * @Description: TODO
// */
//
//import com.hebeu.pojo.Path;
//import com.hebeu.pojo.Role;
//import com.hebeu.pojo.vo.RolePathVo;
//import com.hebeu.service.PathService;
//import com.hebeu.service.RolePathService;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Collection;
//import java.util.List;
//
///**
// * 拦截到当前的请求，并根据请求路径从数据库中查出当前资源路径需要哪些权限才能访问
// */
//@Component
//public class CustomizeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//
//    @Resource
//    private PathService pathService;
//
//    @Resource
//    private RolePathService rolePathService;
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//        //拿到请求地址
//        String requestUrl = ((FilterInvocation)object).getRequestUrl();
//
//        //查询url允许访问的role
//        Path path=new Path();
//        path.setUrl(requestUrl);
//        List<Path> pathList=pathService.select(path);
//        List<RolePathVo> rolePathList = rolePathService.getByPathId(pathList.get(0).getId());
//
//        if(rolePathList.size()==0){
//            return null;
//        }
//
//        //查询到的role集合是一个String的List，我们将其转为String的数组
//        String[] attributes = new String[rolePathList.size()];
//        int i=0;
//        for(RolePathVo rolePath : rolePathList){
//            attributes[i++] = rolePath.getName();
//        }
//
//        //使用SecurityConfig.createList方法，将数组中的String转为ConfigAttribute对象，并形成集合返回
//        return SecurityConfig.createList(attributes);
//
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    //开启
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}