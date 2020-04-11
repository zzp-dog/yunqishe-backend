package com.zx.yunqishe.service;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.dao.UserMapper;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

/**
 * 通用服务
 */
public class CommonService {

    @Autowired
    protected UserMapper userMapper;

    /**
     * 确保能够获取到当前用户
     * 解决静态机服务和客户端sessionid不同步导致客户端取不到session中用户问题
     * @return User
     * @throws Exception
     */
    protected User getCurrentBaseUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getSession().getAttribute("me");
        // 会话中有id直接返回
        if (user != null) {
            return user;
        }
        // 刷新页面，是通过静态机请求地，导致客户端取不到session地值
        String account = (String)subject.getPrincipals().getPrimaryPrincipal();
        if (account == null) return null;

        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        example.selectProperties(new String[]{
                "id", "name", "nickname",
                "avator","experience", "roleNames"
        });
        criteria.andEqualTo("account", account);
        User baseUser = userMapper.selectOneByExample(example);
        subject.getSession().setAttribute("me", baseUser);
        return baseUser;
    }

    /**
     * 获取当前用户id
     * @return
     */
    protected Integer getUserId() {
        User user = getCurrentBaseUser();
        return user == null ? null : user.getId();
    }
}
