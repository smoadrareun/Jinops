package com.hebeu.common;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: AssembleResponseMsg
 * @Author: Smoadrareun
 * @Description: TODO 封装ResponseBody内容
 */

public class AssembleResponseMsg {

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
