package com.zx.yunqishe.common.consts;
/**
 * 接口规范[可选]
 *
 * 如：
 * /topicClass/b/batch/update
 * /topicClass/b/batch/delete
 * /topicClass/b/delete/one
 * /topicClass/b/insert/one
 * /topicClass/b/insertOrUpdate/one
 * /topicClass/b/insertOrUpdate/oneOrList
 * /topicClass/b/batch/insertOrUpdate
 * /topicClass/f/select/list
 * /topicClass/f/select/oneWithChildren
 * /topicClass/f/select/list
 * /topicClass/f/select/xxx/list
 * /topicClass/f/select/xxx/composeData
 * 以上[xxx]中xxx为自定义的修饰词
 */
public class API {

    /** 通用API */
    public static final String OR = "/or";
    public static final String ONE = "/one";
    public static final String LIST = "/list";
    public static final String COUNT = "/count";
    public static final String TRASH = "/trash";
    public static final String BATCH = "/batch";
    public static final String WHERE = "/where";
    public static final String SIMPLE = "/simple";
    public static final String INSERT = "/insert";
    public static final String DELETE = "/delete";
    public static final String UPDATE = "/update";
    public static final String SELECT = "/select";
    public static final String ONE_OR_LIST = "/oneOrList";
    public static final String COMPOSE_DATA = "/composeData";
    public static final String INSERT_OR_UPDATE = "/insertOrUpdate";
    public static final String ONE_WITH_CHILDREN = "/oneWithChildren";

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
    /** 前台和后台公用 */
    public static final String UNIVERSAL = "/u";

    /**  起步 */
    public static final String SETUP = "/setup";
    /** 注册 */
    public static final String REGIST = "/regist";
    /** 登录 */
    public static final String LOGIN = "/login";
    /** 登出 */
    public static final String LOGOUT = "/logout";
    /** 安装 */
    public static final String INSTALL = "/install";
    /** 发送验证码 */
    public static final String SEND_CODE = "/sendcode";
    /** 是否登录和记住我 */
    public static final String IS_RECORD = "/isrecord";
    /** 验证验证码 */
    public static final String VERIFY_CODE = "/verifycode";

    /** 用户api前缀 */
    public static final String USER = "/user";
    /** 角色api前缀 */
    public static final String ROLE = "/role";
    /** 权限api前缀 */
    public static final String POWER = "/power";
    /** 用户等级api前缀 */
    public static final String LEVEL = "/level";

    /** 话题api前缀 */
    public static final String TOPIC_CLASS = "/topicClass";
    /** 话题内容api前缀 */
    public static final String TOPIC_CONTENT = "/topicContent";
    /** 话题内容评论api前缀 */
    public static final String TOPIC_COMMENT = "/topicComment";

    /** 文档分类api前缀 (树）*/
    public static final String DOC_CLASS = "/docClass";
    /** 文档内容api前缀 */
    public static final String DOC_CONTENT = "/docContent" ;

    /** 媒体分类api前缀 (树）*/
    public static final String MEDIA_CLASS = "/mediaClass";
    /** 媒体内容api前缀 */
    public static final String MEDIA_CONTENT = "/mediaContent";

    /** 收藏或关注api前缀 */
    public static final String CONCERN = "/concern";

    /**  点赞或反对api前缀 */
    public static final String THUMB = "/thumb";

    /** 支付记api前缀 */
    public static final String CHARGE = "/charge";

    /** 会员时长相关参数配置前缀 */
    public static final String VIP_ARG = "/vipArg";

    /** 云币兑换参数配置前缀 */
    public static final String EXCHANGE_ARG = "/exchangeArg";
    /** 支付记录表api前缀 */
    public static final String PAY = "/pay";
    /** 站点信息 */
    public static final String SITE_INFO = "/siteInfo";
    /** 开关 */
    public static final String SWITCH = "/switch";
    /** 邮件设置 */
    public static final String EMAIL_DISPOSE = "/emailDispose";
    /** 邮件消息模板 */
    public static final String EMAIL_TEMPLATE = "/emailTemplate";
    /** 图片配置（如轮播等） */
    public static final String BANNER_DISPOSE = "/bannerDispose";
}
