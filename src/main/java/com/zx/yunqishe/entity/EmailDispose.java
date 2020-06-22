package com.zx.yunqishe.entity;

import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/** 邮箱配置+模板内容 */
@Getter
@Setter
@Table(name = "email_dispose")
public class EmailDispose {
    /** 自增id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /** 发送超时ms 使用Callable时(同步发送时，单个线程阻塞)必填 */
    @Column(name = "time_out")
    private Long timeOut;
    /** 发件人 */
    @Column(name = "`from`")
    private String from;
    /** 发送邮件服务器 */
    private String host;
    /** 发件人认证信息 */
    private String auth;
    /** 是否启用0-不启用，1-启用 */
    private Byte active;

    /** 收件人 */
    @Transient
    private String to;
    /** 是否异步：0-同步，1-异步 */
    @Transient
    private Byte async;
    /** 主题 */
    @Transient
    private String subject;
    /** content */
    @Transient
    private String content;
}
