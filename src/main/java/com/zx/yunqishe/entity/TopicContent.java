package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zx.yunqishe.entity.base.Content;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "topic_content")
@Setter
@Getter
public class TopicContent extends Content{
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 作者id
     */
    private Integer uid;

    /**
     * 所属话题的id
     */
    private Integer tid;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String introduce;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 1-精华, 0-否
     */
    private Byte good;

    /**
     * 1-置顶，0-不置顶【可以有多个置顶哦】
     */
    private Byte top;

    /**
     * 1-可改，0-不可改
     */
    @Column(name = "MODIFY")
    private Byte modify;

    /**
     * 1-可见，0-不可见
     */
    private Byte visible;

    /**
     * 封面地址url
     */
    private String cover;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date createTime;

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
     * 内容
     */
    private String text;

    /**
     * 发表时设备
     */
    private Byte device;

    /**
     * 发表时定位，可根据ip粗略定位
     */
    private String address;

    /**
     * 收藏数
     */
    @Column(name = "concern_count")
    private Integer concernCount;

    /**
     * 点赞数
     */
    @Column(name="thumbup_count")
    private Integer thumbupCount;

    /**
     * 反对数
     */
    @Column(name = "thumbdown_count")
    private Integer thumbdownCount;

    /**
     * 转发数
     */
    @Column(name="forward_count")
    private Integer forwardCount;

    /**
     * 所属模块0-圈子，1-问云
     */
    private Byte type;

    /**
     * 内容查看策略 1-免费，2-需支付云币，3-仅需开通vip，4-vip半价
     */
    private Byte strategy;

    /**
     * 状态 1-审核中，2-被退回，3-审核通过
     */
    private Byte status;

    /**
     * 价格多少云币
     */
    private BigDecimal price;

    /**
     * 评论数
     */
    @Column(name = "comment_count")
    private Integer commentCount;

    /**
     * 作者
     */
    @Transient
    private User user;

    /**
     * 所属分类
     */
    @Transient
    private TopicClass topicClass;

    /**
     * 是否被当前用户关注
     */
    @Transient
    private Concern concernInfo;

    /**
     * 是否点赞或反对或都不是
     */
    @Transient
    private Thumb thumbInfo;

    /**
     * 0 - 有权限，1-需要登录，2 - 需全价支付云币，3-用户需要开通会员，4-会员需要开通会员后优惠，5-优惠支付云币（会员特权）
     */
    @Transient
    protected Byte privilegeType;
}