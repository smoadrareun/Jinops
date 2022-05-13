package com.hebeu.common;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: IDUtil
 * @Author: Smoadrareun
 * @Description: TODO ID工具类
 */

public class IDUtil {

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    //生成数字ID
    public static Integer getID(){
        int uuid=UUID.randomUUID().toString().hashCode();
        uuid = uuid < 0 ? -uuid : uuid; //String.hashCode() 值可能为负数
        return uuid;
    }

    //生成UUID
    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    //生成短UUID
    public static String getShortUUID() {
        StringBuilder shortBuffer = new StringBuilder();
        String sUuid = getUUID();
        for (int i = 0; i < 8; i++) {
            String str = sUuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
}

