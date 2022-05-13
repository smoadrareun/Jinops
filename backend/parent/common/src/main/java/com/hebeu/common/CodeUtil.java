package com.hebeu.common;

import org.springframework.util.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MD5Util
 * @Author: Smoadrareun
 * @Description: TODO 加密工具类
 */
public class MD5Util {

    //获取md5加密字符串
    public static String getMD5Str(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }
}
