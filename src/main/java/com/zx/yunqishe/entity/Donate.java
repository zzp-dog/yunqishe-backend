package com.zx.yunqishe.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

public class Donate {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 捐助多少
     */
    @Column(name = "how_much")
    private BigDecimal howMuch;

    /**
     * 捐助时间
     */
    @Column(name = "donate_time")
    private Date donateTime;

    /**
     * 捐助方式
     */
    @Column(name = "donate_way")
    private Byte donateWay;

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
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取捐助多少
     *
     * @return how_much - 捐助多少
     */
    public BigDecimal getHowMuch() {
        return howMuch;
    }

    /**
     * 设置捐助多少
     *
     * @param howMuch 捐助多少
     */
    public void setHowMuch(BigDecimal howMuch) {
        this.howMuch = howMuch;
    }

    /**
     * 获取捐助时间
     *
     * @return donate_time - 捐助时间
     */
    public Date getDonateTime() {
        return donateTime;
    }

    /**
     * 设置捐助时间
     *
     * @param donateTime 捐助时间
     */
    public void setDonateTime(Date donateTime) {
        this.donateTime = donateTime;
    }

    /**
     * 获取捐助方式
     *
     * @return donate_way - 捐助方式
     */
    public Byte getDonateWay() {
        return donateWay;
    }

    /**
     * 设置捐助方式
     *
     * @param donateWay 捐助方式
     */
    public void setDonateWay(Byte donateWay) {
        this.donateWay = donateWay;
    }
}