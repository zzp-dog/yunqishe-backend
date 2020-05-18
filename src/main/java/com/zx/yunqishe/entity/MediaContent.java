package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zx.yunqishe.entity.base.Content;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name="media_content")
@Getter
@Setter
public class MediaContent extends Content{

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父id,-1-总分类无父id
     */
    private Integer pid;

    /**
     * 排序id
     */
    private Integer sid;

    /**
     * 标题
     */
    private String title;

    /**
     * 1-可见，0-不可见
     */
    private Byte visible;

    /**
     * 媒体分类创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date createTime;

    /** 封面 */
    private String cover;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date updateTime;

    /**
     * 摘要
     */
    private String introduce;

    /**
     * 内容链接
     */
    private String url;

    /**
     * 内容查看策略 1-免费，2-需支付云币，3-仅需开通vip，4-vip半价
     */
    private Byte strategy;

    /**
     * 状态 1-审核中，2-审核通过，3-驳回
     */
    private Byte status;

    /**
     * 价格多少云币
     */
    private BigDecimal price;

    /**
     * 0 - 有权限，1-需要登录，2 - 需全价支付云币，3-用户需要开通会员，4-会员需要开通会员后优惠，5-优惠支付云币（会员特权）
     */
    @Transient
    protected Byte privilegeType;
}
