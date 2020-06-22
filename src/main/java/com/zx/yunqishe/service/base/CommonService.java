package com.zx.yunqishe.service.base;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.utils.DateUtil;
import com.zx.yunqishe.dao.ChargeMapper;
import com.zx.yunqishe.dao.PayMapper;
import com.zx.yunqishe.dao.UserMapper;
import com.zx.yunqishe.dao.UserRoleMapper;
import com.zx.yunqishe.entity.*;
import com.zx.yunqishe.entity.base.Content;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * 通用服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommonService {

    @Autowired
    protected UserMapper userMapper;

    @Autowired
    protected ChargeMapper chargeMapper;

    @Autowired
    protected PayMapper payMapper;

    @Autowired
    protected UserRoleMapper userRoleMapper;

    /**
     * 确保能够获取到当前用户
     * @throws Exception
     */
    protected User getCurrentBaseUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getSession().getAttribute("me");
        // 会话中有用户直接返回
        if (null != user) {
            return user;
        }
        PrincipalCollection principalCollection = subject.getPrincipals();
        if (null == principalCollection) return null;
        String account = (String)principalCollection.getPrimaryPrincipal();
        if (null == account) return null;
        User baseUser = userMapper.selectBaseOneByAccount(account);
        //  查询是否会员过期
        Integer uid = baseUser.getId();
        List<Pay> pays = payMapper.selectPaysWithVipArgByUid(uid);
        for (Pay pay : pays) {
            VipArg vipArg = pay.getVipArg();
            Byte type = vipArg.getType();
            Integer duration = vipArg.getDuration();
            Date openTime = pay.getPayTime();
            Long diff = DateUtil.diffNow(openTime);
            if (duration * 30 * 24 * 3600 * 1000 > diff) { // 未过期
                continue;
            }
            // 过期了，则删除记录
            UserRole userRole = new UserRole();
            userRole.setUserId(uid);
            List<String> roleNames = baseUser.getRoleNames();
            if (1==type) { // vip
                userRole.setRoleId(5);
                userRoleMapper.delete(userRole);
                roleNames.remove("vip");
            } else if(2 == type){ // svip
                userRole.setRoleId(4);
                userRoleMapper.delete(userRole);
                roleNames.remove("svip");
            }
        }
        subject.getSession().setAttribute("me", baseUser);
        return baseUser;
    }

    /**
     * 获取当前用户id
     * @return
     */
    protected Integer getUserId() {
        User user = getCurrentBaseUser();
        return null == user ? null : user.getId();
    }

    /**
     * 判断用户是否svip
     * @return
     */
    protected Boolean isSVip() {
        User user = getCurrentBaseUser();
        if (null == user) {
            return false;
        }
        List<String> roleNames = user.getRoleNames();
        for (String roleName : roleNames) {
            if (roleName.equals("svip")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否vip
     * @return
     */
    protected Boolean isVip() {
        User user = getCurrentBaseUser();
        if (null == user) {
            return false;
        }
        List<String> roleNames = user.getRoleNames();
        for (String roleName : roleNames) {
            if (roleName.equals("vip")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 两个功能：<br/>
     * 1.判断用户是否能够查看内容<br/>
     * 2.设置内容权限用于前端展示
     * @param content 需要付费或开通vip才能查看的内容
     * @return object - object[0]:true-表示有权限，false-表示无权限；object[1]:无权限时的错误信息
     */
    protected Object[] getPrivilege(Content content) {

        // 内容查看策略 1-免费，2-需支付云币，3-仅需开通vip，4-vip半价
        Byte  strategy = content.getStrategy();

        // 免费
        if (1 == strategy) {
            content.setPrivilegeType((byte)0);
            return new Object[]{true};
        }

        // 是否登录
        User user = getCurrentBaseUser();
        if(null == user) {
            content.setPrivilegeType((byte)1);
            return new Object[]{false, ErrorMsg.NOT_LOGIN};
        }

        // 只需要开通vip，且该用户是vip
        if (3 == strategy && isVip()) {
            content.setPrivilegeType((byte)0);
            return new Object[]{true};
        }

        // 需要开通vip，但该用户不是vip
        if ((3 == strategy || 4 == strategy) && !isVip()) {
            content.setPrivilegeType(strategy);
            return new Object[]{false,ErrorMsg.NEED_VIP};
        }

        // 查询是否存在云币支付记录
        Charge charge = new Charge();
        charge.setUid1(user.getId());
        charge.setOid(content.getId());
        charge.setType(content.getType());
        charge.setPrice(content.getPrice());
        Integer count = chargeMapper.selectCount(charge);

        if (1 == count) {
            content.setPrivilegeType((byte)0);
            return new Object[]{true};
        }

        Byte privilege = 2 == strategy ? (byte)2 : 5;
        content.setPrivilegeType(privilege);
        return new Object[]{false,ErrorMsg.NEED_CHARGE};
    }
}
