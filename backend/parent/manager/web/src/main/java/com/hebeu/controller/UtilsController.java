package com.hebeu.controller;

import com.hebeu.common.VerUtil;
import com.hebeu.common.SmsUtil;
import com.hebeu.common.MailUtil;
import com.hebeu.pojo.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/Jinops/utils")
public class UtilsController {
    public ResponseBody msg = new ResponseBody();

    private MailUtil mailUtil;

    @Autowired
    private void setMailUtil(MailUtil mailUtil){
        this.mailUtil=mailUtil;
    }

    private SmsUtil smsUtil;

    @Autowired
    private void setSmsUtil(SmsUtil smsUtil){
        this.smsUtil=smsUtil;
    }

    //获取验证码
    @RequestMapping("/getVerCode/{length}")
    public ResponseBody getVerCode(@PathVariable("length") Integer length) {
        Map<String,String> map= new HashMap<>();
        String verCode= VerUtil.getRandom(length);
        map.put("verCode",verCode);
        map.put("verImage", VerUtil.getCodeImage(verCode));
        return msg.success("验证码获取成功",map);
    }

    @RequestMapping("/getImg")
    public ResponseBody getImg(@RequestBody Map<String,Object> map) {
        Map<String,String> maps= new HashMap<>();
        MultipartFile file= (MultipartFile) map.get("img");
        file.getContentType();
        return msg.success("验证码获取成功",maps);
    }

    @RequestMapping("/sendMail")
    public ResponseBody sendMail(@RequestBody Map<String,Object> map) {
        if(map.get("type").equals("1")){
            mailUtil.sendSimpleMail(map.get("to").toString(),map.get("subject").toString(),map.get("content").toString());
        }else if(map.get("type").equals("2")){
            mailUtil.sendHtmlMail(map.get("to").toString(),map.get("subject").toString(),map.get("content").toString());
        }else if(map.get("type").equals("3")){
            mailUtil.sendAttachmentMail(map.get("to").toString(),map.get("subject").toString(),map.get("content").toString(),map.get("filePath").toString());
        }else if(map.get("type").equals("4")){
            mailUtil.sendInlineResourceMail(map.get("to").toString(),map.get("subject").toString(),map.get("content").toString(),map.get("rscPath").toString(),map.get("String rscId").toString());
        }
        return msg.success("邮件发送成功",null);
    }

    @RequestMapping("/sendSms/{phone}")
    public ResponseBody sendSms(@PathVariable("phone") Long phone) {
        Boolean aBoolean=smsUtil.SendSms(String.valueOf(phone), VerUtil.getRandomNumber(6).toString(),"5");
        if(aBoolean==null){
            return msg.failure(-1,"短信验证码发送失败");
        }else if(!aBoolean){
            return msg.failure(-400,"输入的手机号不符合规范");
        }else{
            return msg.success("短信验证码发送成功",null);
        }
    }

}
