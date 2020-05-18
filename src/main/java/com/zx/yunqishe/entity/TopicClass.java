package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Table(name="topic_class")
public class TopicClass {
    /**
     * 自增节id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 排序id
     */
    @NotNull
    private Byte sid;

    /**
     * 创建者id，-1-系统
     */
    private Integer uid;

    /**
     * 名称
     */
    @NotNull
    @NotBlank
    @Column(name = "NAME")
    private String name;

    /**
     * 1-可见，0-不可见
     */
    @NotNull
    private Byte visible;

    /**
     * 0-非问题，1-问题
     */
    @NotNull
    private Byte type;

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
     * 内容数目
     */
    @Column(name = "content_count")
    private Integer contentCount;

    /**
     * 关注数
     */
    @Column(name = "concern_count")
    private Integer concernCount;

    /**
     * 描述
     */
    private String description;

    /**
     * 封面
     */
    private String cover;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     * 当前用户关注信息
     */
    @Transient
    private Concern concernInfo;
}