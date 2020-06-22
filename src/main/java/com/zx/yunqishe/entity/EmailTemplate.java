package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 邮件内容模板
 */
@Table(name="email_template")
@Getter
@Setter
public class EmailTemplate {
    /** 自增id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 是否异步，默认0-同步 */
    private Byte async;
    /** 类型：1-验证码 */
    private Byte type;
    private String subject;
    /** 要发送的消息模板 */
    private String template;
    /** 过期时间，ms */
    private Integer expire;
}
