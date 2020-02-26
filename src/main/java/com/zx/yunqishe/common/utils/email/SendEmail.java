package com.zx.yunqishe.common.utils.email;

import lombok.Data;

@Data
public class SendEmail {
    /** 发送类型： code - 发送验证码，link-发送验证连接 */
    private String type;
    /** 连接 type为link时必填 如http://example.com/verifycode?code=xxx*/
    private String link;
    /** 发送超时ms 使用Callable时必填 */
    private Long sendTimeout;
    /** 验证码 type为code时必填*/
    private String code;
    /** 发件人 */
    private String from;
    /** 收件人 */
    private String to;
    /** 发送邮件服务器 */
    private String host;
    /** 发件人认证信息 */
    private String auth;
}
