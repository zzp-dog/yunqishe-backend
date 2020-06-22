package com.zx.yunqishe.common.consts;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常错误信息
 */
public class ErrorMsg {

    /** 系统错误 */
    public static final String ERROR_401 = "未授权";
    public static final String ERROR_403 = "禁止访问";
    public static final String ERROR_404 = "请求的资源暂未找到";
    public static final String ERROR_500 = "系统繁忙，请稍后重试";

    /** 未找到相关内容 */
    public static final String NOT_FOUND_CONTENT = "抱歉,未找到相关内容~";

    /** http请求 */
    public static final String REQUEST_REPEAT = "不能重复请求~";

    /** 安全错误 */
    public static final String GET_SK_ERROR = "获取密钥失败";
    public static final String SK_EXPIRE_ERROR = "密钥已失效";
    public static final String REQUEST_DECRYPT_ERROR = "请求参数解密异常";
    public static final String ENCRYPT_ERROR = "响应加密失败";

    /** 参数验证错误 */
    public static final String NOT_NULL = "不能为空";
    public static final String NOT_BLANK = "不能为空字符串";
    public static final String TOKEN_ERROR = "登录状态(TOKEN)异常";
    public static final String ARG_VALID_ERROR = "请求参数验证错误";

    /** User */
    public static final String REGIST_ERROR = "注册失败";
    public static final String CODE_ERROR = "验证码有误";
    public static final Object NOT_LOGIN = "您还未登录！";
    public static final String LOGIN_ERROR = "账号或密码错误";
    public static final String SEND_EMAIL_ERROR = "邮件发送失败";
    public static final String NAME_ERROR = "姓名不符合校验规则";
    public static final String NICK_ERROR = "昵称不符合校验规则";
    public static final String CODE_STATUS_ERROR = "验证未通过!";
    public static final String CODE_TIMEOUT_ERROR = "验证码已超时";
    public static final String ACCOUNT_EXIST_ERROR = "该账号已存在";
    public static final String EMAIL_EXIST_ERROR = "邮箱已被注册";
    public static final String ACCOUNT_ERROR = "账号不符合校验规则";
    public static final String PASSWORD_ERROR = "密码不符合校验规则";
    public static final String KEEP_LOGIN_ERROR = "登录已超时,请重新登录";
    public static final String ADMIN_EXISTS_ERROR = "系统管理员已存在";
    public static final String ERROR_PERMISSION = "权限不足!";
    public static final String CANNOT_TRASH = "无法删除";
    public static final String ROLE_BOUND = "存在角色限制问题!";

    /** role */
    public static final String POWER_BOUND = "存在权限限制问题!";
    public static final String ROLE_BEUSED = "角色被占用,操作失败";
    public static final String ROLE_BEUSEDBYCHILD = "存在角色被子角色占用,操作失败";

    /** power */
    public static final String PARENT_BOUND = "存在父级限制问题!";
    public static final String POWER_BEUSED = "权限被占用,操作失败";
    public static final String POWER_BEUSEDBYCHILD = "存在权限被子权限占用,操作失败";

    /** docClass */
    public static final String CLASS_USED = "该类别被占用,无法删除";

    /** VipArg */
    public static final String NEED_VIP = "需要先开通会员哦~";
    /** 需要先支付云币哦~ */
    public static final Object NEED_CHARGE = "需要先支付云币哦~";
    /** 云币不足~ */
    public static final String COIN_SHORTAGE = "云币不足~";
    /** 上传失败 */
    public static final String UPLOAD_ERROR = "上传失败";
    /** 至少保留一项！ */
    public static final String RETAIN_ONE = "至少保留一项！";
}
