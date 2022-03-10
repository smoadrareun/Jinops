package com.hebeu.controller;


import com.hebeu.common.AssembleResponseMsg;
import com.hebeu.common.ResponseBody;
import com.hebeu.common.VerCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Jinops/utils")
public class UtilsController {
    public AssembleResponseMsg msg = new AssembleResponseMsg();

    //获取验证码
    @RequestMapping("/getVerCode/{length}")
    public ResponseBody getVerCode(@PathVariable("length") Integer length) {
        Map<String,String> map= VerCode.getVerCode(length);
        return msg.success("验证码获取成功",map);
    }

}
