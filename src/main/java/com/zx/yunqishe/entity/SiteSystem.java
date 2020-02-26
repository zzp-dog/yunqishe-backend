package com.zx.yunqishe.entity;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "site_system")
public class SiteSystem {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 网站名称
     */
    @Column(name = "site_name")
    private String siteName;

    /**
     * 存活天数
     */
    @Column(name = "keep_days")
    private Integer keepDays;

    /**
     * 0-邮箱，1-短信
     */
    @Column(name = "regist_verify_way")
    private Byte registVerifyWay;

    /**
     * 1-可注册，0-不可注册
     */
    @Column(name = "sign_up")
    private Byte signUp;

    /**
     * 1-可登录，0-不可登录
     */
    @Column(name = "sign_in")
    private Byte signIn;

    /**
     * 版本号
     */
    private BigDecimal version;

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
     * 获取网站名称
     *
     * @return site_name - 网站名称
     */
    public String getSiteName() {
        return siteName;
    }

    /**
     * 设置网站名称
     *
     * @param siteName 网站名称
     */
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    /**
     * 获取存活天数
     *
     * @return keep_days - 存活天数
     */
    public Integer getKeepDays() {
        return keepDays;
    }

    /**
     * 设置存活天数
     *
     * @param keepDays 存活天数
     */
    public void setKeepDays(Integer keepDays) {
        this.keepDays = keepDays;
    }

    /**
     * 获取0-邮箱，1-短信
     *
     * @return regist_verify_way - 0-邮箱，1-短信
     */
    public Byte getRegistVerifyWay() {
        return registVerifyWay;
    }

    /**
     * 设置0-邮箱，1-短信
     *
     * @param registVerifyWay 0-邮箱，1-短信
     */
    public void setRegistVerifyWay(Byte registVerifyWay) {
        this.registVerifyWay = registVerifyWay;
    }

    /**
     * 获取1-可注册，0-不可注册
     *
     * @return sign_up - 1-可注册，0-不可注册
     */
    public Byte getSignUp() {
        return signUp;
    }

    /**
     * 设置1-可注册，0-不可注册
     *
     * @param signUp 1-可注册，0-不可注册
     */
    public void setSignUp(Byte signUp) {
        this.signUp = signUp;
    }

    /**
     * 获取1-可登录，0-不可登录
     *
     * @return sign_in - 1-可登录，0-不可登录
     */
    public Byte getSignIn() {
        return signIn;
    }

    /**
     * 设置1-可登录，0-不可登录
     *
     * @param signIn 1-可登录，0-不可登录
     */
    public void setSignIn(Byte signIn) {
        this.signIn = signIn;
    }

    /**
     * 获取版本号
     *
     * @return version - 版本号
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}