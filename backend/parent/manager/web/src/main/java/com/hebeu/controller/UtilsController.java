package com.hebeu.controller;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hebeu.common.VerUtil;
import com.hebeu.common.SmsUtil;
import com.hebeu.common.MailUtil;
import com.hebeu.common.ResponseBody;
import com.hebeu.pojo.vo.MailVo;
import com.hebeu.pojo.vo.SmsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: UtilsController
 * @Author: Smoadrareun
 * @Description: TODO 工具组件控制层实现类
 */

@CrossOrigin
@RestController
@RequestMapping("/Jinops/utils")
public class UtilsController {
    public ResponseBody<Object> resp = new ResponseBody<>();

    private MailUtil mailUtil;

    @Autowired
    private void setMailUtil(MailUtil mailUtil) {
        this.mailUtil = mailUtil;
    }

    private SmsUtil smsUtil;

    @Autowired
    private void setSmsUtil(SmsUtil smsUtil) {
        this.smsUtil = smsUtil;
    }

    //获取验证码
    @RequestMapping("/getVerCode/{length}")
    public ResponseBody<Object> getVerCode(@PathVariable("length") Integer length) {
        Map<String, String> map = new HashMap<>();
        String verCode = VerUtil.getRandom(length);
        map.put("verCode", verCode);
        map.put("verImage", VerUtil.getCodeImage(verCode));
        return resp.success("验证码获取成功", map);
    }

    @RequestMapping("/sendMail")
    public ResponseBody<Object> sendMail(@RequestBody MailVo mailVo) {
        Boolean aBoolean = null;
        switch (mailVo.getType()) {
            case 1: {
                aBoolean = mailUtil.sendHtmlMail(mailVo.getTo(), mailVo.getSubject(), mailVo.getContent());
            }
            case 2: {
                aBoolean = mailUtil.sendAttachmentMail(mailVo.getTo(), mailVo.getSubject(), mailVo.getContent(), mailVo.getFilePath());
            }
            case 3: {
                aBoolean = mailUtil.sendInlineResourceMail(mailVo.getTo(), mailVo.getSubject(), mailVo.getContent(), mailVo.getRscPath(), mailVo.getRscId());
            }
            default: {
                aBoolean = mailUtil.sendSimpleMail(mailVo.getTo(), mailVo.getSubject(), mailVo.getContent());
            }
        }
        if (aBoolean == null || !aBoolean) {
            return resp.failure(-1, "邮件发送失败");
        } else {
            return resp.success("短信验证码发送成功", mailVo);
        }
    }

    @RequestMapping("/sendSms")
    public ResponseBody<Object> sendSms(@RequestBody SmsVo smsVo) {
        if(ObjectUtils.isEmpty(smsVo.getLength())||smsVo.getLength()>6){
            smsVo.setLength(6);
        }
        smsVo.setCode(VerUtil.getRandomNumber(smsVo.getLength()));
        Boolean aBoolean = smsUtil.SendSms(String.valueOf(smsVo.getPhone()), smsVo.getCode(), String.valueOf(smsVo.getMinute()));
        if (aBoolean == null) {
            return resp.failure(-1, "短信验证码发送失败");
        } else if (!aBoolean) {
            return resp.failure(-400, "输入的手机号不符合规范");
        } else {
            return resp.success("短信验证码发送成功", smsVo);
        }
    }

}
