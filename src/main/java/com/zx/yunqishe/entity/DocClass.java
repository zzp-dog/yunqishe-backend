package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name="doc_class")
public class DocClass {
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
     * 分类层级
     */
    private Integer level;

    /**
     * 内容查看策略 1-免费，2-需支付云币，3-仅需开通vip，4-vip半价
     */
    private Byte strategy;

    /**
     * 价格多少云币
     */
    private BigDecimal price;

    /** 子内容 */
    @Transient
    private List<DocClass> docClasses;

}