package com.zx.yunqishe.common.shiro;

import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.Role;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 获取权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String account = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        User user = userService.selectUserWithRolesWithPowers(account);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getName());

            //添加权限
            for (Power permissions : role.getPowers()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getName());
            }

        }

        return simpleAuthorizationInfo;
    }

    /**
     * 获取用户认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在请求的时候会先进认证，然后在到请求
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        if (token.getPrincipal() == null) {
            return null;
        }
        //获取用户信息-账号
        String account = token.getUsername();
        User user = userService.queryUserByAccount(account);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        }
        //这里验证authenticationToken和simpleAuthenticationInfo的信息
        return new SimpleAuthenticationInfo(account, user.getPassword(), getName());
    }

}

