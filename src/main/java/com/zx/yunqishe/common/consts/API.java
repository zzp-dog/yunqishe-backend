package com.zx.yunqishe.common.consts;

public class API {

    /** 401,404 , 403,500*/
    public static final String ERROR = "/error";
    public static final String MY_ERROR= "/myerror";

    /** 安全类 */
    public static final String SECURITY = "/security";
    /** 请求公钥 */
    public static final String GET_PK = "/getpk";
    /** 上送私钥 */
    public static final String SEND_SK = "/sendsk";

    /** crud */
    public static final String OR = "/or";
    public static final String ONE = "/one";
    public static final String LIST = "/list";
    public static final String TRASH = "/trash";
    public static final String BATCH = "/batch";
    public static final String SIMPLE = "/simple";
    public static final String INSERT = "/insert";
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";
    public static final String SELECT = "/select";

    /** 用户类 */
    public static final String USER = "/user";
    /** 是否登录和记住我 */
    public static final String IS_RECORD = "/isrecord";
    /** 登录 */
    public static final String LOGIN = "/login";
    /** 注册 */
    public static final String REGIST = "/regist";
    /** 安装 */
    public static final String INSTALL = "/install";
    /** 发送验证码 */
    public static final String SEND_CODE = "/sendcode";
    /** 验证验证码 */
    public static final String VERIFY_CODE = "/verifycode";
    /**  查询是否存在超级管理 */
    public static final String QUERY_ADMIN = "/queryadmin";
    /** 登出 */
    public static final String LOGOUT = "/logout";

    /** 角色类api */
    public static final String ROLE = "/role";
    /** 权限类api */
    public static final String POWER = "/power";
}
