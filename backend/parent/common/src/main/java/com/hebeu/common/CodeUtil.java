package com.hebeu.common;

import org.springframework.util.DigestUtils;
import java.util.Base64;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MD5Util
 * @Author: Smoadrareun
 * @Description: TODO 编码工具类
 */
public class CodeUtil {

    //获取md5加密字符串
    public static String getMD5Str(String str,String salt) {
        return DigestUtils.md5DigestAsHex((str+salt).getBytes());
    }

    //base64字符串转为二进制数组
    public static byte[] base64ToByte(String str) {
        byte[] bytes =null;
        try {
             bytes = Base64.getDecoder().decode(str.toString().replace("\r\n", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }

    //二进制数组转为base64字符串
    public static String byteToBase64(byte[] bytes) {
        String result = null;
        try {
            result = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //去除base64中的image头
    public static String removeHeader(String str) {
        try {
            str=str.substring(str.indexOf(",")+1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
