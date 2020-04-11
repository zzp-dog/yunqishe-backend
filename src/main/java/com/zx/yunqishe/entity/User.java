package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.consts.RegExp;
import com.zx.yunqishe.common.utils.Generator;
import com.zx.yunqishe.common.utils.ValidatorUtil;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.CacheNamespaceRef;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
// shiro记住我，User类需要实现序列化接口！！！
// mybatis 二级缓存的查询也需要实现序列化接口
@Getter
@Setter
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
    @NotBlank
    @Pattern(regexp = RegExp.REG_NAME, message = ErrorMsg.NAME_ERROR)
    @Column(name = "NAME")
    private String name;

    /**
     * 昵称
     */
    @NotBlank
    @Pattern(regexp = RegExp.REG_OHER_NAME, message = ErrorMsg.NAME_ERROR)
    private String nickname;


    /**
     * 账号
     */
    @NotBlank
    @Pattern(regexp = RegExp.REG_NAME, message = ErrorMsg.ACCOUNT_ERROR)
    private String account;

    /**
     * 密码
     */
    @NotBlank
    @Pattern(regexp = RegExp.REG_MD5, message = ErrorMsg.PASSWORD_ERROR)
    @Column(name = "PASSWORD")
    private String password;

    /**
     * 头像
     */
    private String avator;

    /**
     * 手机号
     */
    private String phone;

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
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private String lastLoginTime;

    /**
     * 上次登出时间
     */
    @Column(name = "last_logout_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
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

    /**
     * 被关注数
     */
    private Integer concern;

    /**
     * 等级
     */
    @Transient
    private Integer level;

    @Transient
    private List<Role> roles;

    /** 用户角色字符串，避免连查 */
    @Column(name = "role_names")
    private String roleNames;

    /** 合作商 0-否，1-否 */
    private Byte partner;

}