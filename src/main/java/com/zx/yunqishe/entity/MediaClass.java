package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name="media_class")
public class MediaClass {

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
     * 名称
     */
    @Column(name = "NAME")
    private String name;

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
     * 作者id
     */
    private Integer uid;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     *  二级分类媒体观看次数，作为它的热度
     */
    @Column(name="view_count")
    private Integer viewCount;

    /**
     * 创建者
     */
    @Transient
    private User user;

    /** 媒体子内容分类 */
    @Transient
    private List<MediaClass> mediaClasses;

}
