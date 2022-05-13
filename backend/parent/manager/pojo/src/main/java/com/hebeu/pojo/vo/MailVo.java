package com.hebeu.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * @ClassName: Vendor
 * @Author: Smoadrareun
 * @Description: TODO 邮件信息控制层实体类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 邮件类型
     */
    private Integer type;

    /**
     * 收件人
     */
    private String to;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String content;

    /**
     * 附件路径
     */
    private String filePath;

    /**
     * 图片路径
     */
    private String rscPath;

    /**
     * 图片ID
     */
    private String rscId;

}


