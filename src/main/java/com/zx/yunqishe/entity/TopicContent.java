package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Table(name = "topic_content")
@Setter
@Getter
public class TopicContent {
    /**
     * 自增节id
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
    @Column(name = "view")
    private Integer view;

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
     * 1-收费，0-不收费
     */
    private Byte charge;

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
    private Integer concern;

    /**
     * 点赞数
     */
    private Integer thumbup;

    /**
     * 反对数
     */
    private Integer thumbdown;

    /**
     * 转发数
     */
    private Integer forward;

    /**
     * 所属模块0-论坛，1-问云
     */

    private Byte wt;
    /**
     * 作者姓名
     */
    @Transient
    private User user;

    /**
     * 分类名称
     */
    @Transient
    private Topic topic;

    /**
     * 评论数
     */
    @Column(name = "comment_count")
    private Integer commentCount;

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
}