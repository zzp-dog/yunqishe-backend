package com.zx.yunqishe.entity;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.consts.RegExp;
import com.zx.yunqishe.common.utils.Generator;
import com.zx.yunqishe.common.utils.ValidatorUtil;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
// shiro记住我，User类需要实现序列化接口！！！
// mybatis 二级缓存的查询也需要实现序列化接口
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 姓名
     */
    @Pattern(regexp = RegExp.REG_NAME, message = ErrorMsg.NAME_ERROR)
    @Column(name = "NAME")
    private String name;

    /**
     * 昵称
     */
    @Pattern(regexp = RegExp.REG_OHER_NAME, message = ErrorMsg.NAME_ERROR)
    private String nickname;

    /**
     * 头像
     */
    private String avator;

    /**
     * 手机号
     */
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 邮箱
     */
    @Email
    private String email;
    /**
     * qq
     */
    private String qq;

    /**
     * wechat
     */
    private String wechat;

    /**
     * 地址
     */
    private String address;

    /**
     * 0-男,1-女,-1-保密
     */
    private Byte sex;

    /**
     * 留言
     */
    private String say;

    /**
     * 发表数量
     */
    @Column(name = "deliver_count")
    private Integer deliverCount;

    /**
     * 0-普通用户,1-vip,2-版主,3-超级管理
     */
    @Column(name = "role_id")
    private Integer roleId;

    /**
     * 等级经验
     */
    private Integer experience;

    /**
     * 称号
     */
    private String designation;

    /**
     * 金币
     */
    private Integer coin;

    /**
     * 年龄
     */
    private Byte age;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 上次登出时间
     */
    @Column(name = "last_logout_time")
    private Date lastLogoutTime;

    /**
     * 登出ip地址
     */
    @Column(name = "last_logout_ip")
    private Long lastLogoutIp;

    /**
     * 登录ip地址
     */
    @Column(name = "last_login_ip")
    private Long lastLoginIp;

    /**
     * 上次登录设备0-pc,1-mobile
     */
    @Column(name = "last_login_device")
    private Byte lastLoginDevice;

    /**
     * 上次登出设备0-pc,1-mobile
     */
    @Column(name = "last_logout_device")
    private Byte lastLogoutDevice;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 0-未验证,1-正常,2-禁封，3-销毁
     */
    @Column(name = "STATUS")
    private Byte status;

    /**
     * 保持登录token
     */
    private String token;

    /**
     * 验证码
     */
    @Column(name = "CODE")
    private String code;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * 用户角色

     */
//    @Transient
    private List<Role> roles;
    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取姓名
     *
     * @return NAME - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取昵称
     *
     * @return neck - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取头像
     *
     * @return avator - 头像
     */
    public String getAvator() {
        return avator;
    }

    /**
     * 设置头像
     *
     * @param avator 头像
     */
    public void setAvator(String avator) {
        this.avator = avator;
    }

    /**
     * 获取手机号
     *
     * @return phone - 手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置手机号
     *
     * @param phone 手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取qq
     *
     * @return qq - qq
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置qq
     *
     * @param qq qq
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取wechat
     *
     * @return wechat - wechat
     */
    public String getWechat() {
        return wechat;
    }

    /**
     * 设置wechat
     *
     * @param wechat wechat
     */
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取0-男,1-女,-1-保密
     *
     * @return sex - 0-男,1-女,-1-保密
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置0-男,1-女,-1-保密
     *
     * @param sex 0-男,1-女,-1-保密
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取留言
     *
     * @return say - 留言
     */
    public String getSay() {
        return say;
    }

    /**
     * 设置留言
     *
     * @param say 留言
     */
    public void setSay(String say) {
        this.say = say;
    }

    /**
     * 获取发表数量
     *
     * @return deliver_count - 发表数量
     */
    public Integer getDeliverCount() {
        return deliverCount;
    }

    /**
     * 设置发表数量
     *
     * @param deliverCount 发表数量
     */
    public void setDeliverCount(Integer deliverCount) {
        this.deliverCount = deliverCount;
    }

    /**
     * 获取0-普通用户,1-vip,2-版主,3-超级管理
     *
     * @return role_id - 0-普通用户,1-vip,2-版主,3-超级管理
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置0-普通用户,1-vip,2-版主,3-超级管理
     *
     * @param roleId 0-普通用户,1-vip,2-版主,3-超级管理
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取等级经验
     *
     * @return experience - 等级经验
     */
    public Integer getExperience() {
        return experience;
    }

    /**
     * 设置等级经验
     *
     * @param experience 等级经验
     */
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    /**
     * 获取称号
     *
     * @return designation - 称号
     */
    public String getDesignation() {
        return designation;
    }

    /**
     * 设置称号
     *
     * @param designation 称号
     */
    public void setDesignation(String designation) {
        this.designation = designation;
    }

    /**
     * 获取金币
     *
     * @return coin - 金币
     */
    public Integer getCoin() {
        return coin;
    }

    /**
     * 设置金币
     *
     * @param coin 金币
     */
    public void setCoin(Integer coin) {
        this.coin = coin;
    }

    /**
     * 获取年龄
     *
     * @return age - 年龄
     */
    public Byte getAge() {
        return age;
    }

    /**
     * 设置年龄
     *
     * @param age 年龄
     */
    public void setAge(Byte age) {
        this.age = age;
    }

    /**
     * 获取上次登录时间
     *
     * @return last_login_time - 上次登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置上次登录时间
     *
     * @param lastLoginTime 上次登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 获取上次登出时间
     *
     * @return last_logout_time - 上次登出时间
     */
    public Date getLastLogoutTime() {
        return lastLogoutTime;
    }

    /**
     * 设置上次登出时间
     *
     * @param lastLogoutTime 上次登出时间
     */
    public void setLastLogoutTime(Date lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    /**
     * 获取登出ip地址
     *
     * @return last_logout_ip - 登出ip地址
     */
    public Long getLastLogoutIp() {
        return lastLogoutIp;
    }

    /**
     * 设置登出ip地址
     *
     * @param lastLogoutIp 登出ip地址
     */
    public void setLastLogoutIp(Long lastLogoutIp) {
        this.lastLogoutIp = lastLogoutIp;
    }

    /**
     * 获取登录ip地址
     *
     * @return last_login_ip - 登录ip地址
     */
    public Long getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置登录ip地址
     *
     * @param lastLoginIp 登录ip地址
     */
    public void setLastLoginIp(Long lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * 获取上次登录设备0-pc,1-mobile
     *
     * @return last_login_device - 上次登录设备0-pc,1-mobile
     */
    public Byte getLastLoginDevice() {
        return lastLoginDevice;
    }

    /**
     * 设置上次登录设备0-pc,1-mobile
     *
     * @param lastLoginDevice 上次登录设备0-pc,1-mobile
     */
    public void setLastLoginDevice(Byte lastLoginDevice) {
        this.lastLoginDevice = lastLoginDevice;
    }

    /**
     * 获取上次登出设备0-pc,1-mobile
     *
     * @return last_logout_device - 上次登出设备0-pc,1-mobile
     */
    public Byte getLastLogoutDevice() {
        return lastLogoutDevice;
    }

    /**
     * 设置上次登出设备0-pc,1-mobile
     *
     * @param lastLogoutDevice 上次登出设备0-pc,1-mobile
     */
    public void setLastLogoutDevice(Byte lastLogoutDevice) {
        this.lastLogoutDevice = lastLogoutDevice;
    }

    /**
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * 获取密码
     *
     * @return PASSWORD - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取0-正常,1-禁封,2-销毁
     *
     * @return STATUS - 0-正常,1-禁封,2-销毁
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置0-正常,1-禁封,2-销毁
     *
     * @param status 0-正常,1-禁封,2-销毁
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取保持登录token
     *
     * @return token - 保持登录token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置保持登录token
     *
     * @param token 保持登录token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 获取验证码
     *
     * @return CODE - 验证码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置验证码
     *
     * @param code 验证码(备用-不适合作为邮箱或短信验证码这类存贮时间较短的码值，它们可以存在session或redis中）
     */
    public void setCode(String code) {
        this.code = code;
    }
}