package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 评论表，与话题内容表分开，减少数据冗余^_^
 */
@Entity
@Table(name = "topic_comment")
@Getter
@Setter
public class TopicComment {
    
    /**
     * 自增节id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 所属话题内容id
     */
    private Integer tcid;

    /**
     * 父评论id，-1表示顶级评论
     */
    private Integer pid;

    /**
     * 评论内容
     */
    private String text;

    /**
     * 评论创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;

    /**
     * 评论更新时间
     */
    @Column(name = "update_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;

    /**
     * 评论者
     */
    private Integer uid;

    /**
     * 回复者
     */
    private Integer wid;

    /**
     * 是否神评
     */
    private Byte good;

    /**
     * 是否可见
     */
    private Byte visible;

    /**
     * 是否收费
     */
    private Byte charge;

    /**
     * 回复时所用设备
     */
    private Byte device;

    /**
     * 子级回复数量
     */
    @Column(name = "comment_count")
    private Integer commentCount;

    /**
     * 回复者
     */
    @Transient
    private User user;

    /**
     * 对谁回复
     */
    @Transient
    private User who;

    /**
     * 子回复
     * @return
     */
    @Transient
    private List<TopicComment> comments;
    /**
     * 回复时定位
     */
    private String address;

    /**
     * 点赞
     */
    private Integer thumbup;
    /**
     * 反对
     */
    private Integer thumbdown;

    /**
     * 当前用户点赞或反对信息
     */
    @Transient
    private Thumb thumbInfo;
}
