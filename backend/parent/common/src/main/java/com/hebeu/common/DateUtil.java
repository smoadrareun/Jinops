package com.hebeu.common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: DateUtil
 * @Author: Smoadrareun
 * @Description: TODO 日期工具类
 */

public
class DateUtil {

    /**
     * 返回字符串形式的当前日期
     * @Param [pattern]  模板参数  如："yyyy-MM-dd HH:mm:ss"
     * @return java.lang.String
     **/

    //获取当前时间
    public static String getCurrentDateStr(String pattern){
        DateTime dateTime=new DateTime();
        return dateTime.toString(pattern);
    }

    //获取当前时间戳
    public static Long getCurrentTimeMill(){
        return new DateTime().getMillis();
    }

    //时间转化为时间戳
    public static Long DateToTimeMill(String currentDateStr,String pattern){
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        Long currentTimeMill=null;
        try {
            DateTime dateTime=formatter.parseDateTime(currentDateStr);
            currentTimeMill=dateTime.getMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currentTimeMill;
    }

    //时间戳转换为时间
    public static String TimeMillToDate(Long currentTimeMill,String pattern){
        DateTime dateTime=new DateTime(currentTimeMill);
        return dateTime.toString(pattern);
    }
}

