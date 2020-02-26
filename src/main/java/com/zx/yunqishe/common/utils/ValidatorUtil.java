package com.zx.yunqishe.common.utils;

import java.util.regex.Pattern;

public class ValidatorUtil {

    /**
     * 正则表达式：验证名称
     */
    public static final String REG_NAME= "(^[a-z0-9_-]{4,16}$)|(^[\\u2E80-\\u9FFF]{2,5}$)";

    /**
     * 正则表达式：验证其他名称
     */
    public static final String REG_OHER_NAME = "^[a-z0-9\\u2E80-\\u9FFF_-]+$";
    /**
     * 正则表达式：验证一般密码（不含特殊字符）
     */
    public static final String REG_COMMON_PASSWORD = "^[a-z0-9_,.@/-]{6,18}$";

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
    /**
     * 校证账号
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isName(String str) {
        return Pattern.matches(REG_NAME, str);
    }

    /**
     * 校验一般密码
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isCommonPassword(String str) {
        return Pattern.matches(REG_COMMON_PASSWORD, str);
    }

    /**
     * 校验强密码
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isStrongPassword(String str) {
        return Pattern.matches(REG_STRONG_PASSWORD, str);
    }

    /**
     * 校验电话号
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhone(String str) {
        return Pattern.matches(REG_PHONE, str);
    }

    /**
     * 校验手机号
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String str) {
        return Pattern.matches(REG_MOBILE, str);
    }

    /**
     * 校验邮箱
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String str) {
        return Pattern.matches(REG_EMAIL, str);
    }

    /**
     * 校验身份证
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String str) {
        return Pattern.matches(REG_ID_CARD, str);
    }

    /**
     * 校验正整数
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPositiveInteger(String str) {
        return Pattern.matches(REG_POSITIVE_INTEGER, str);
    }

    /**
     * 校验正数
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPositiveNumber(String str) {
        return Pattern.matches(REG_POSITIVE_NUMBER, str);
    }

    /**
     * 校验负整数
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNegativeInteger(String str) {
        return Pattern.matches(REG_NEGATIVE_INTEGER, str);
    }

    /**
     * 校验负数
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNegativeNumber(String str) {
        return Pattern.matches(REG_NEGATIVE_NUMBER, str);
    }

    /**
     * 校验字母、数字>6
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isNumberOrLetterGT6(String str) {
        return Pattern.matches(REG_NUMBER_LETTER_GT6, str);
    }

    /**
     * 校验QQ
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isQQ(String str) {
        return Pattern.matches(REG_QQ, str);
    }

    /**
     * 校验微信
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isWchat(String str) {
        return Pattern.matches(REG_WCHAT, str);
    }

    /**
     * 校验金额
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMoney(String str) {
        return Pattern.matches(REG_MONEY, str);
    }

    /**
     * 校验ISBN
     * @param str
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isISBN(String str) {
        return Pattern.matches(REG_ISBN, str);
    }
    /**
     * 校验自然数
     * @param str
     * @return
     */
    public static boolean isNaturalNumber(String str){
        return Pattern.matches(REG_NATURAL_NUMBER, str);
    }
    /**
     *
     * @param str
     * @return
     */
    public static boolean isOhterName(String str) {
        return Pattern.matches(REG_OHER_NAME, str);
    }

    public static void main(String [] args){
        System.out.println(Double.parseDouble(null));
        System.out.println(isNegativeNumber("-0"));
        System.out.println(isNegativeNumber("-1"));
        System.out.println(isNegativeNumber("-1.11"));
        System.out.println(isNegativeNumber("-0.01"));

    }

}
