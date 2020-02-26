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

    // 应该是从数据查的，先写死
    public static final String[] OPEN_APIS = {
            API.SECURITY + API.GET_PK,
            API.SECURITY + API.SEND_SK,
            API.USER + API.IS_RECORD,
            API.USER + API.SEND_CODE,
            API.USER + API.VERIFY_CODE,
            API.USER + API.QUERY_ADMIN,
            API.USER + API.REGIST,
            API.USER + API.INSTALL,
            API.USER + API.LOGIN,
            API.USER + API.LOGOUT
    };

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录api
        shiroFilterFactoryBean.setLoginUrl("/user/login");
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
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdsgUs0FSA3SDFAdag=="));
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
