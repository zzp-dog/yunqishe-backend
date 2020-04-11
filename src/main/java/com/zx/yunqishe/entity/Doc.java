package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Doc {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 父id,-1-总分类无父id
     */
    protected Integer pid;

    /**
     * 排序id
     */
    protected Integer sid;

    /**
     * 名称
     */
    @Column(name = "NAME")
    protected String name;

    /**
     * 1-可见，0-不可见
     */
    protected Byte visible;

    /**
     * 1-收费，0-不收费
     */
    protected Byte charge;

    /**
     * 文档分类创建时间
     */
    @Column(name = "create_time")
    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    protected Date createTime;

    /** 封面 */
    protected String cover;

    /**
     * 类型1-总分类，2-文档，3-章
     */
    private Byte type;

    /** 子内容 */
    @Transient
    private List<Doc> docs;

}