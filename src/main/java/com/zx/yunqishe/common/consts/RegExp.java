package com.zx.yunqishe.common.consts;

public class RegExp {

    /**
     * 正则表达式：验证名称
     */
    public static final String REG_NAME= "(^[a-z0-9_-]{4,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";

    /**
     * 正则表达式：验证其他名称
     */
    public static final String REG_OHER_NAME = "^[a-z0-9\\u2E80-\\u9FFF_-]+$";

    /**
     * md5
     */
    public static final String REG_MD5 = "^[0-9A-Za-z]{32}$";

    /**
     * 正则表达式：验证一般密码（不含特殊字符）
     */
    public static final String REG_COMMON_PASSWORD = "^[a-z0-9_-]{6,18}$";

    /**
     * 正则表达式：验证强密码
     */
    public static final String REG_STRONG_PASSWORD = "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";

    /**
     * 正则表达式：验证电话
     */
    public static final String REG_PHONE = "^(([0+]d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(d{3,}))?$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REG_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REG_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式：验证身份证
     */
    public static final String REG_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";

    /**
     * 正则表达式：验证正整数
     */
    public static final String REG_POSITIVE_INTEGER = "^0*[1-9][0-9]*$";
    /**
     * 正则表达式：验证负整数
     */
    public static final String REG_NEGATIVE_INTEGER = "^-[0-9]*[1-9][0-9]*$";
    /**
     * 正则表达式：验证正数
     */

    public static final String REG_POSITIVE_NUMBER = "^0*[1-9][0-9]*(\\.[0-9]+)?$";
    /**
     * 正则表达式：验证负数
     */

    public static final String REG_NEGATIVE_NUMBER = "^-0*[1-9][0-9]*(\\.[0-9]+)?$";
    /**
     * 正则表达式：自然数
     */
    public static final String REG_NATURAL_NUMBER = "^[0-9]+$";

    /**
     * 正则表达式：验证QQ
     */
    public static final String REG_QQ = "^[1-9][0-9]{4,10}$";

    /**
     * 正则表达式：验证微信
     */
    public static final String REG_WCHAT = "^[a-zA-Z]([-_a-zA-Z0-9]{5,19})+$";

    /**
     * 正则表达式：验证金额
     */
    public static final String REG_MONEY = "(^[1-9]([0-9]{0,4})(\\.[0-9]{1,2})$)|(^[1-9]([0-9]{0,4})$)|(^[0-9]\\.[0-9]{1,2}$)";

    /**
     * 正则表达式：验证ISBN
     */
    public static final String REG_ISBN = "^97[89][0-9]{10}$";

    /**
     * 正则表达式：数字、字母>6
     */
    public static final String REG_NUMBER_LETTER_GT6 = "^[0-9A-Za-z]{6,}$";

    /** 验证码 */
    public static final String VERIFY_CODE = "^\\d{6}$";
}
