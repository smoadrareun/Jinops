package com.hebeu.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MailUtil
 * @Author: Smoadrareun
 * @Description: TODO 邮箱工具类
 */

@Slf4j
@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String username;

    //用于发送文件
    private JavaMailSender mailSender;

    @Autowired
    private void setMailSender(JavaMailSender mailSender){
        this.mailSender=mailSender;
    }

//    public JavaMailSenderImpl createMailSender() {
//        JavaMailSenderImpl sender = new JavaMailSenderImpl();
//        sender.setHost(host);
//        sender.setPort(port==null? 0:port);
//        sender.setUsername(username);
//        sender.setPassword(password);
//        sender.setDefaultEncoding("Utf-8");
//        Properties p = new Properties();
//        p.setProperty("mail.smtp.starttls.required", "true");
//        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//        sender.setJavaMailProperties(p);
//        return sender;
//    }

    /**
     * 发送普通文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public Boolean sendSimpleMail(String to, String subject, String content) {
        log.info("发送普通文本邮件开始，请求参数：{},{},{}", to, subject, content);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);//收信人
            message.setSubject(subject);//主题
            message.setText(content);//内容
            message.setFrom(username);//发信人
            mailSender.send(message);
            log.info("发送普通文本邮件成功");
        } catch (Exception e) {
            log.error("发送普通文本邮件失败：", e);
            return false;
        }
        return true;
    }

    /**
     * 发送html邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容(可以包含html等标签)
     */
    public Boolean sendHtmlMail(String to, String subject, String content) {
        log.info("发送html邮件开始，请求参数：{},{},{}", to, subject, content);
        try {
            //使用MimeMessage，MIME协议
            MimeMessage message = mailSender.createMimeMessage();
            //MimeMessageHelper帮助我们设置更丰富的内容
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
            log.info("发送HTML邮件成功");
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败：", e);
            return false;
        }
        return true;
    }


    /**
     * 发送带附件邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
    public Boolean sendAttachmentMail(String to, String subject, String content, String filePath) {
        log.info("发送带附件邮件开始，请求参数：{},{},{},{}", to, subject, content, filePath);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
            log.info("发送带附件邮件成功");
        } catch (MessagingException e) {
            log.error("发送带附件邮件失败", e);
            return false;
        }
        return true;
    }

    /**
     * 发送带图片邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param rscPath 图片路径
     * @param rscId   rscId 图片ID,用于在<img\>标签中使用，从而显示图片
     */
    public Boolean sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        log.info("发送带图片邮件开始，请求参数：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            log.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            log.error("发送带图片邮件失败", e);
            return false;
        }
        return true;
    }
}
