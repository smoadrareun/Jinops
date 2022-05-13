package com.hebeu.common;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MapUtil
 * @Author: Smoadrareun
 * @Description: TODO Map工具类
 */
public class MapUtil {
    public static <T> T mapToObj(Map map,Class<T> clazz){
        try {
            Field[] fields=clazz.getDeclaredFields();
            T t=clazz.getDeclaredConstructor().newInstance();
            for(Field field:fields){
                Object obj;
                if((obj=map.get(field.getName()))!=null){
                    field.setAccessible(true);
                    field.set(t,obj);
                }
            }
            return t;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
