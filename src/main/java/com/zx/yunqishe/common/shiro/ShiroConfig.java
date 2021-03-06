package com.zx.yunqishe.common.shiro;
import com.zx.yunqishe.common.consts.API;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述：
 *
 * @author caojing
 * @create 2019-01-27-13:38
 */
@Configuration
public class ShiroConfig {

    // 开放api，不需要登录，用ajax请求时需要同源，开发时可用前端node.js代理
    public static final String[] OPEN_APIS = {
            // 全局404等错误
            API.ERROR+API.MY_ERROR,
            // 客户端获取公钥
            API.SECURITY+API.GET_PK,
            // 客户端上送密钥
            API.SECURITY+API.SEND_SK,
            // 用户是否登录或被记住
            API.USER+API.IS_RECORD,
            // 发送验证码
            API.USER+API.SEND_CODE,
            // 验证验证码
            API.USER+API.VERIFY_CODE,
            // 应用起始点
            API.USER+API.SETUP,
            // 用户注册
            API.USER+API.REGIST,
            // 用户安装
            API.USER+API.INSTALL,
            // 用户登录
            API.USER+API.LOGIN,
            // 用户后台登录
            API.USER + API.BACKEND + API.LOGIN,
            // 用户登出
            API.USER+API.LOGOUT,
            // 前台查询活跃用户
            API.USER+API.FRONTEND+API.SELECT+"/active"+API.LIST,
            // 首页用户列表
            API.USER + API.FRONTEND + API.SELECT + API.LIST,

            // 前台查询经验等级表
            API.LEVEL+API.FRONTEND+API.SELECT+API.LIST,

            // 前台查询某个话题分类
            API.TOPIC_CLASS+API.FRONTEND+API.SELECT+API.ONE,
            // 前台查询话题分类列表
            API.TOPIC_CLASS+API.FRONTEND+API.SELECT+API.LIST,

            // 前台查询某一话题内容
            API.TOPIC_CONTENT+API.FRONTEND+API.SELECT+API.ONE,
            // 前台查询话题内容列表
            API.TOPIC_CONTENT+API.FRONTEND+API.SELECT+API.LIST,
            // 台查询话题分类之问答推荐分类列表
            API.TOPIC_CONTENT+API.FRONTEND+API.SELECT+"/recommend"+API.LIST,
            // 前台查询tab列表，接口请求合并
            API.TOPIC_CONTENT + API.FRONTEND + API.SELECT + "/tab" +API.LIST,
            // 前台查询话题内容回复列表
            API.TOPIC_COMMENT+API.FRONTEND+API.SELECT+API.LIST,

            // 前台查询文档前两级分类
            API.DOC_CLASS+API.FRONTEND+API.SELECT+"/top2lv"+API.LIST,
            // 前台查询文档后两级分类
            API.DOC_CLASS+API.FRONTEND+API.SELECT+"/end2lv"+API.LIST,
            // 文档之节
            API.DOC_CONTENT+API.FRONTEND+API.SELECT+API.ONE,

            // 媒体前两级分类
            API.MEDIA_CLASS+API.FRONTEND+API.SELECT+"/top2lv"+API.LIST,
            // 媒体二级分类和其子内容
            API.MEDIA_CLASS+API.FRONTEND+API.SELECT+API.ONE_WITH_CHILDREN,
            // 请求媒体内容
            API.MEDIA_CONTENT+API.FRONTEND+API.SELECT+API.ONE,

            // banner配置
            API.BANNER_DISPOSE + API.FRONTEND + API.SELECT + API.LIST
    };

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录api
        //shiroFilterFactoryBean.setLoginUrl("/user/login");
        // 设置开放api
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        for(String api: OPEN_APIS) {
            filterChainDefinitionMap.put(api, "anon");
        }
        // 非开放api【记住我的配置为user】
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    //权限认证信息
    @Bean(name = "securityManager")
    public DefaultSecurityManager securityManager(CustomRealm customRealm, EhCacheManager ehCacheManager,RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        // 设置领域realm
        defaultSecurityManager.setRealm(customRealm);
        // shiro安全管理器设置认证/授权缓存
        defaultSecurityManager.setCacheManager(ehCacheManager);
        // 注入记住我管理器
        defaultSecurityManager.setRememberMeManager(rememberMeManager);
        return defaultSecurityManager;
    }

    @Bean
    public CustomRealm customRealm(EhCacheManager ehCacheManager) {
        CustomRealm customRealm = new CustomRealm();
        // customRealm注入缓存管理器
        customRealm.setCacheManager(ehCacheManager);
        //告诉realm,使用credentialsMatcher加密算法类来验证密文
        return customRealm;
    }

    // 生命周期交由spring管理
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /// 开启注解
    /**
     * *
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * *
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * * @return
     */
    //DefaultAdvisorAutoProxyCreator实现Spring自动代理
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultSecurityManager defaultSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /// rememberMe管理器
    /**
     * cookie对象;
     * rememberMeCookie()方法是设置Cookie的生成模版，比如cookie的name，cookie的有效时间等等。
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setPath("/"); // 所有请求都带上该cookie
        simpleCookie.setHttpOnly(true); // 禁止js访问cookie，防止xss
        //<!-- 记住我cookie生效时间7天 ,单位秒;-->
        simpleCookie.setMaxAge(7 * 24 * 3600);
        return simpleCookie;
    }

    /**
     * rememberMe管理对象;
     * rememberMeManager()方法是生成rememberMe管理器，而且要将这个rememberMe管理器设置到securityManager中
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie simpleCookie){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 字节数组长度要求16
        cookieRememberMeManager.setCipherKey("zzp0113636044291".getBytes());
        cookieRememberMeManager.setCookie(simpleCookie);
        return cookieRememberMeManager;
    }

    /// 权限缓存管理器
    /**
     * 权限缓存管理器，避免多次查询
     * @return
     */
    @Bean(name="ehcacheManager")
    public EhCacheManager getEhCacheManager(){
         EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehcacheManager;
    }

}
