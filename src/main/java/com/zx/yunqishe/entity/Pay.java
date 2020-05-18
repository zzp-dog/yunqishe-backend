package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付记录表
 */
@Getter
@Setter
public class Pay {
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
     * 支付时间
     */
    @Column(name = "pay_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date payTime;

    /**
     * 参数配置表(exchange_arg和vip_arg) id
     */
    private Integer aid;

    /**
     * 1-云币（开通会员才有的类型），2-支付宝，3-微信
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 1-兑换云币，2-开通vip，3-开通svip
     */
    @Column(name = "for_type")
    private Byte forType;

    /**
     * 支付多少，云币（for_type是开通会员才有这个）或人名币
     */
    private BigDecimal value;

    /**
     * 会员时长配置表
     */
    @Transient
    private VipArg vipArg;

    /**
     * 云币兑换配置表
     */
    @Transient
    private ExchangeArg exchangeArg;
}
