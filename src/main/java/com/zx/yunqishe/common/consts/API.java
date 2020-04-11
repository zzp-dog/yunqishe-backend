package com.zx.yunqishe.common.consts;
/**
 * 接口规范，非必需按照这个来
 *
 * /实体/端/[特殊功能]/[批量]/操作/[单个或列表]
 * 其中[xxx]表示xxx可选
 * 如：
 * /topic/b/forum/batch/update  => 后台批量更新话题【类别：论坛】
 * /topic/f/select/list         => 前台查询话题列表
 */
public class API {

    /** 401,404 , 403,500*/
    public static final String ERROR = "/error";
    public static final String MY_ERROR= "/myerror";

    /** 安全类api前缀 */
    public static final String SECURITY = "/security";
    /** 请求公钥 */
    public static final String GET_PK = "/getpk";
    /** 上送私钥 */
    public static final String SEND_SK = "/sendsk";

    /** 后台 */
    public static final String BACKEND = "/b";
    /** 前台 */
    public static final String FRONTEND = "/f";

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
    public static final String ONE_OR_LIST = "/oneOrList";
    public static final String INSERT_OR_UPDATE = "/insertOrUpdate";

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

    /** 用户api前缀 */
    public static final String USER = "/user";
    /** 角色api前缀 */
    public static final String ROLE = "/role";
    /** 权限api前缀 */
    public static final String POWER = "/power";
    /** 话题api前缀 */
    public static final String TOPIC = "/topic";
    /** 用户等级api前缀 */
    public static final String LEVEL = "/level";
    /** 话题内容api前缀 */
    public static final String TOPIC_CONTENT = "/topicContent";
    /** 话题内容评论api前缀 */
    public static final String TOPIC_COMMENT = "/topicComment";
    /** 收藏或关注api前缀 */
    public static final String CONCERN = "/concern";
    /**  点赞或反对api前缀 */
    public static final String THUMB = "/thumb";
    /** 文档分类api前缀 */
    public static final String DOC = "/doc";
    /** 文档内容api前缀 */
    public static final String DOC_CONTENT = "/docContent" ;
}
