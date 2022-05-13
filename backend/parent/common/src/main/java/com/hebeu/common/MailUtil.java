package com.hebeu.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 *
 * @ClassName: MailUtil
 * @Author: Smoadrareun
 * @Description: TODO 邮箱工具类
 */

public class MailUtil {
    // 日志
    private static final Logger logger = LoggerFactory.getLogger(MailUtil.class);

    private static String HOST="smtp.qq.com";
    private static Integer PORT=587;
    private static String USERNAME="1751271104@qq.com";
    private static String PASSWORD="xvfrkkxvesnzdhac";
//
//    @Value("${spring.mail.host}")
//    private String host;
//    @Value("${spring.mail.port}")
//    private Integer port;
//    @Value("${spring.mail.username}")
//    private String username;
//    @Value("${spring.mail.password}")
//    private String password;
//
//    @PostConstruct
//    public void init() {
//        HOST = host;
//        PORT = port;
//        USERNAME = username;
//        PASSWORD = password;
//        System.out.println(host+"-"+port+"-"+username+"-"+password);
//    }

    //用于发送文件
    private static final JavaMailSender mailSender=createMailSender();

    private static JavaMailSenderImpl createMailSender() {
        System.out.println(HOST+"--"+PORT+"--"+USERNAME+"--"+PASSWORD);
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.starttls.required", "true");
        p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送普通文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    public static void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);//收信人
        message.setSubject(subject);//主题
        message.setText(content);//内容
        message.setFrom(USERNAME);//发信人

        mailSender.send(message);
    }

    /**
     * 发送html邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容(可以包含html等标签)
     */
    public static void sendHtmlMail(String to, String subject, String content) {
        logger.info("发送html邮件开始：{},{},{}", to, subject, content);
        //使用MimeMessage，MIME协议
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        //MimeMessageHelper帮助我们设置更丰富的内容
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(USERNAME);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);//true代表支持html
            mailSender.send(message);
            logger.info("发送HTML邮件成功");
        } catch (MessagingException e) {
            e.printStackTrace();
            logger.error("发送HTML邮件失败：", e);
        }

    }


    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
    public static void sendAttachmentMail(String to, String subject, String content, String filePath) {
        // 日志
        logger.info("发送带附件邮件开始：{},{},{},{}", to, subject, content, filePath);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            //true代表支持多组件，如附件，图片等
            helper.setFrom(USERNAME);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = file.getFilename();
            helper.addAttachment(fileName, file);//添加附件，可多次调用该方法添加多个附件
            mailSender.send(message);
            logger.info("发送带附件邮件成功");
        } catch (MessagingException e) {
            logger.error("发送带附件邮件失败", e);
        }

    }

    /**
     * 发送带图片的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param rscPath 图片路径
     * @param rscId   rscId 图片ID,用于在<img\>标签中使用，从而显示图片
     */
    public static void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {
        // 日志
        logger.info("发送带图片邮件开始：{},{},{},{},{}", to, subject, content, rscPath, rscId);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(USERNAME);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);//重复使用添加多个图片
            mailSender.send(message);
            logger.info("发送带图片邮件成功");
        } catch (MessagingException e) {
            logger.error("发送带图片邮件失败", e);
        }
    }
}
