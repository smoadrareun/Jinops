package com.hebeu.common;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: SmsUtil
 * @Author: Smoadrareun
 * @Description: TODO 短信工具类
 */

@Slf4j
@Configuration
public class SmsUtil {
    @Value("${tencent.sms.secretId}")
    private String secretId;
    @Value("${tencent.sms.secretKey}")
    private String secretKey;
    @Value("${tencent.sms.smsSdkAppId}")
    private String smsSdkAppId;
    @Value("${tencent.sms.templateId}")
    private String templateId;
    @Value("${tencent.sms.signName}")
    private String signName;

    public Boolean SendSms(String phone, String code, String minute) {
        log.info("发送短信验证码开始，请求参数：{},{},{}", phone, code, minute);
        String regex = "^1[3|5|7|8][0-9]\\d{8}$";
        if (ObjectUtils.isEmpty(phone) || !phone.matches(regex)) {
            log.error("发送短信验证码失败：输入的手机号不符合规范");
            return false;
        }
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential(secretId, secretKey);
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            req.setPhoneNumberSet(new String[]{phone});
            req.setSmsSdkAppId(smsSdkAppId);
            req.setSignName(signName);
            req.setTemplateId(templateId);

            String[] templateParamSet = {code, minute};
            req.setTemplateParamSet(templateParamSet);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            if (!resp.getSendStatusSet()[0].getCode().equals("Ok")) {
                throw new Exception(resp.getSendStatusSet()[0].getCode());
            }
            log.info("发送短信验证码成功，返回参数：{}", SendSmsResponse.toJsonString(resp));
        } catch (Exception e) {
            log.error("发送短信验证码失败：", e);
            return null;
        }
        return true;
    }
}
