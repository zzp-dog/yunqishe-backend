package com.zx.yunqishe.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zx.yunqishe.entity.Concern;
import com.zx.yunqishe.entity.Thumb;
import com.zx.yunqishe.entity.TopicClass;
import com.zx.yunqishe.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户的所有动态列表
 */
@Setter
@Getter
public class Content {

    /**
     * 自增id
     */
    private Integer id;


    /**
     * 作者id
     */
    protected Integer uid;

    /**
     * 父id
     */
    protected Integer pid;

    /**
     * 标题
     */
    protected String title;

    /**
     * 摘要
     */
    protected String introduce;

    /**
     * 1-可见，0-不可见
     */
    protected Byte visible;

    /**
     * 封面地址url
     */
    protected String cover;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    protected Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    protected Date updateTime;


    /**
     * 发表时设备
     */
    protected Byte device;

    /**
     * 发表时定位，可根据ip粗略定位
     */
    protected String address;

    /**
     * 浏览次数
     */
    @Column(name = "view_count")
    protected Integer viewCount;

    /**
     * 收藏数
     */
    @Column(name = "concern_count")
    protected Integer concernCount;

    /**
     * 点赞数
     */
    @Column(name="thumbup_count")
    protected Integer thumbupCount;

    /**
     * 转发数
     */
    @Column(name="forward_count")
    protected Integer forwardCount;

    /**
     * 评论数
     */
    @Column(name = "comment_count")
    protected Integer commentCount;

    /**
     * 内容查看策略 1-免费，2-需全价支付云币，3-仅需开通vip，4-vip半价
     */
    protected Byte strategy;

    /**
     * 状态 1-审核中，2-审核通过，3-驳回
     */
    protected Byte status;

    /**
     * 价格多少云币
     */
    protected BigDecimal price;

    /**
     * 置顶，可以多个置顶，也可只设置某行为置顶1，其他为0
     */
    protected Byte top;

    /**
     * 是否优质内容或被采纳，可以有多个，最好只设置一个
     */
    protected Byte good;

    /**
     * 0-圈子，1-问答，4-图片，5-音乐，6-视频
     */
    protected Byte type;

    /**
     * 0 - 有权限，1-需要登录，2 - 需全价支付云币，3-用户需要开通会员，4-需要开通会员后优惠，5-优惠支付云币（会员特权）
     */
    @Transient
    protected Byte privilegeType;

}