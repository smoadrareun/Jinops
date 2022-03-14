package com.hebeu.controller;

import com.hebeu.common.AssembleResponseMsg;
import com.hebeu.common.ResponseBody;
import com.hebeu.common.VerCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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

    @RequestMapping("/getImg")
    public ResponseBody getImg(@RequestBody Map<String,Object> maps) {
        Map<String,String> map= new HashMap<>();
        MultipartFile file= (MultipartFile) maps.get("img");
        file.getContentType();
        return msg.success("验证码获取成功",map);
    }

}
