//package com.hebeu.security;
//
//import com.alibaba.fastjson.JSON;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created with IntelliJ IDEA.
// *
// * @ClassName: CustomizeAuthenticationEntryPoint
// * @Author: Smoadrareun
// * @Description: TODO
// */
//
//@Component
//public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", 199);
//        map.put("msg", "用户未登录");
//        map.put("success", false);
//        String json = JSON.toJSONString(map);
//
//        response.setContentType("text/json;charset=utf-8");
//        response.getWriter().write(json);
//    }
//}
