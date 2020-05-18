package com.zx.yunqishe.service;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.utils.DateUtil;
import com.zx.yunqishe.dao.*;
import com.zx.yunqishe.entity.*;
import com.zx.yunqishe.entity.core.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class PayService {

    @Autowired
    private PayMapper payMapper;

    @Autowired
    private VipArgMapper vipArgMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 单一插入支付记录表
     * @param pay
     * @param me
     * @return
     */
    public ResponseData fInsertOne(Pay pay, User me) {
        Byte forType = pay.getForType(); // 1-兑换云币，2-开通vip会员，3-开通svip
        Byte payType = pay.getPayType(); // 1-云币（云币不用来兑换云币，2-支付宝，3-微信）
        pay.setPayTime(new Date());      // 设置支付时间,一定要在服务端设置，因为这个是要用于计算会员过期时间的！！！
        // 兑换云币
        if (1 == forType) {
            return null;
        }
        if (2 == forType) { // 开通vip,非续期
            if (1 == payType) { // 使用云币充值vip
                return this.openVipByYB(pay, me);
            }
            return null;
        }
        if (3 == forType) { // 开通svip

        }
        return ResponseData.success();
    }

    /**
     * 用云币开通vip普通会员
     * @param pay - 支付表单
     * @param me - 当前用户
     * @return
     */
    public ResponseData openVipByYB(Pay pay, User me) {
        Integer uid = me.getId();
        Integer aid = pay.getAid();
        pay.setUid(uid);

        // 用户的云币
        BigDecimal coin2 = me.getCoin();
        // 查该时长会员需要的云币
        VipArg vipArg = vipArgMapper.selectByPrimaryKey(aid);
        BigDecimal coin1 = vipArg.getCoin();
        Integer duration = vipArg.getDuration();
        BigDecimal subtract = coin2.subtract(coin1);
        // 云币不足
        if (-1 == subtract.compareTo(BigDecimal.ZERO)) {
            return ResponseData.error(ErrorMsg.COIN_SHORTAGE);
        }

        // 更新数据库用户云币
        me.setCoin(subtract);
        userMapper.updateByPrimaryKeySelective(me);
        // 设置session中当前用户云币
        me.setCoin(subtract);

        // 查询是否已开通，已开通则续费
        Pay pay1 = payMapper.selectOne(pay);
        if (null != pay1) {
            this.updatePayPayTime(pay, duration);
        }

        // 插入支付表单记录
        pay.setValue(coin1);
        payMapper.insert(pay);

        // 插入用户角色表
        Role role1 = new Role();
        role1.setName("vip");
        Role role2 = roleMapper.selectOne(role1);
        Integer rid = role2.getId();
        UserRole userRole = new UserRole();
        userRole.setRoleId(rid);
        userRole.setUserId(uid);
        userRoleMapper.insertSelective(userRole);

        // 设置session中当前用户角色
        List<String> roleNames = me.getRoleNames();
        if (null == roleNames) {
            roleNames = new ArrayList<>();
            me.setRoleNames(roleNames);
        }
        roleNames.add("vip");
        return ResponseData.success();
    }

    /**
     * 会员续费
     * @param pay      - 支付记录
     * @param duration -  会员续费时间
     */
    private void updatePayPayTime(Pay pay, Integer duration) {
        Date date = pay.getPayTime();
        Long time1 = date.getTime();
        Long time2 = time1 + duration * 30 * 24 * 3600 * 1000;
        date.setTime(time2);
        payMapper.updateByPrimaryKeySelective(pay);
    }
}
