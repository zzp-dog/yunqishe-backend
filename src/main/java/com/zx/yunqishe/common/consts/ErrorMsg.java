package com.zx.yunqishe.common.consts;

/**
 * 异常错误信息
 */
public class ErrorMsg {

    /** 系统错误 */
    public static final String ERROR_401 = "未授权";
    public static final String ERROR_403 = "禁止访问";
    public static final String ERROR_404 = "请求的资源暂未找到";
    public static final String ERROR_500 = "系统繁忙，请稍后重试";

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
    public static final String LOGIN_ERROR = "账号或密码错误";
    public static final String SEND_EMAIL_ERROR = "邮件发送失败";
    public static final String NAME_ERROR = "姓名不符合校验规则";
    public static final String NICK_ERROR = "昵称不符合校验规则";
    public static final String CODE_TIMEOUT_ERROR = "验证码已超时";
    public static final String ACCOUNT_EXIST_ERROR = "该账号已存在";
    public static final String EMAIL_EXIST_ERROR = "邮箱已被注册";
    public static final String ACCOUNT_ERROR = "账号不符合校验规则";
    public static final String PASSWORD_ERROR = "密码不符合校验规则";
    public static final String KEEP_LOGIN_ERROR = "登录已超时,请重新登录";
    public static final String ADMIN_EXISTS_ERROR = "系统管理员已存在";
    public static final String ERROR_PERMISSION = "权限不足!";
    public static final String CANNOT_TRASH = "无法删除";
    public static final String CANNOT_UPDATE = "不可更新";

    /** role */
    public static final String ROLE_BEUSED = "角色被占用,操作失败";
    public static final String ROLE_BEUSEDBYCHILD = "存在角色被子角色占用,操作失败";
    public static final String ROLE_PID_ERROR = "不能设置自身或下级角色为父角色";
    public static final String ROLE_POWER_BOUND = "权限不能超过父角色";

    /** power */
    public static final String POWER_BEUSED = "权限被占用,操作失败";
    public static final String POWER_BEUSEDBYCHILD = "存在权限被子权限占用,操作失败";
    public static final String POWER_PID_ERROR = "不能设置自身或下级权限为父权限";
}
