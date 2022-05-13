package com.hebeu.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: DateUtil
 * @Author: Smoadrareun
 * @Description: TODO 请求工具类
 */

public class HttpUtil {

    public static HttpServletRequest getHttpServletRequest(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
