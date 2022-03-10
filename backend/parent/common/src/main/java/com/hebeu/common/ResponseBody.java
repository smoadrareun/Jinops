package com.hebeu.common;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: ResponseBody
 * @Author: Smoadrareun
 * @Description: TODO 封装响应的数据结构
 */

@Getter
@Setter
@NoArgsConstructor
public class ResponseBody<T> implements Serializable {
    //UUID
    private String UUID = IDUtil.getUUID();
    //时间
    private String date = DateUtil.getCurrentDateStr("yyyy-MM-dd HH:mm:ss");
    //状态码 默认为200
    private Integer status=200;
    //信息码
    private Integer code;
    //详情信息
    private String message;
    //接口返回的数据
    private T data;
}

