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

    /**
     * 成功返回内容
     * @Param [data]
     * @return model.ResponseBody
     **/

    @SuppressWarnings({"unchecked", "varargs"})
    public <T> ResponseBody success(String message,T... data){
        ResponseBody<T> resp=new ResponseBody<T>();
        resp.setCode(0);
        resp.setMessage(message);
        if(data.length>0)
            resp.setData(data[0]);
        return resp;
    }

    /**
     * 失败/异常返回内容
     * @Param [status, errorCode, message]
     * @return model.ResponseBody
     **/

    public <T> ResponseBody failure(Integer errorCode,String message){
        ResponseBody<T> resp=new ResponseBody<T>();
        resp.setCode(errorCode);
        resp.setMessage(message);
        return resp;
    }
}