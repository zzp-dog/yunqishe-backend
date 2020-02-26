package com.zx.yunqishe.entity;

import java.util.Date;
import javax.persistence.*;

public class Topic {
    /**
     * 自增节id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 排序id
     */
    private Byte sid;

    /**
     * 创建者id，-1-系统
     */
    private Integer uid;

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
     * 0-非问题，1-问题
     */
    private Byte wt;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取自增节id
     *
     * @return id - 自增节id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增节id
     *
     * @param id 自增节id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取排序id
     *
     * @return sid - 排序id
     */
    public Byte getSid() {
        return sid;
    }

    /**
     * 设置排序id
     *
     * @param sid 排序id
     */
    public void setSid(Byte sid) {
        this.sid = sid;
    }

    /**
     * 获取创建者id，-1-系统
     *
     * @return uid - 创建者id，-1-系统
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置创建者id，-1-系统
     *
     * @param uid 创建者id，-1-系统
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取名称
     *
     * @return NAME - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取1-可见，0-不可见
     *
     * @return visible - 1-可见，0-不可见
     */
    public Byte getVisible() {
        return visible;
    }

    /**
     * 设置1-可见，0-不可见
     *
     * @param visible 1-可见，0-不可见
     */
    public void setVisible(Byte visible) {
        this.visible = visible;
    }

    /**
     * 获取0-非问题，1-问题
     *
     * @return wt - 0-非问题，1-问题
     */
    public Byte getWt() {
        return wt;
    }

    /**
     * 设置0-非问题，1-问题
     *
     * @param wt 0-非问题，1-问题
     */
    public void setWt(Byte wt) {
        this.wt = wt;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}