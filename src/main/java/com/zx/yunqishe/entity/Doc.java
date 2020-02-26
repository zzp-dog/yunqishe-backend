package com.zx.yunqishe.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Doc {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父id,-1-总分类无父id
     */
    private Byte pid;

    /**
     * 排序id
     */
    private Byte sid;

    /**
     * 名称
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 类型0-总分类，1-文档，2-章
     */
    private Byte type;

    /**
     * 1-可见，0-不可见
     */
    private Byte visible;

    /**
     * 1-收费，0-不收费
     */
    private Byte charge;

    /**
     * 获取自增id
     *
     * @return id - 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增id
     *
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取父id,-1-总分类无父id
     *
     * @return pid - 父id,-1-总分类无父id
     */
    public Byte getPid() {
        return pid;
    }

    /**
     * 设置父id,-1-总分类无父id
     *
     * @param pid 父id,-1-总分类无父id
     */
    public void setPid(Byte pid) {
        this.pid = pid;
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
     * 获取类型0-总分类，1-文档，2-章
     *
     * @return type - 类型0-总分类，1-文档，2-章
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型0-总分类，1-文档，2-章
     *
     * @param type 类型0-总分类，1-文档，2-章
     */
    public void setType(Byte type) {
        this.type = type;
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
     * 获取1-收费，0-不收费
     *
     * @return charge - 1-收费，0-不收费
     */
    public Byte getCharge() {
        return charge;
    }

    /**
     * 设置1-收费，0-不收费
     *
     * @param charge 1-收费，0-不收费
     */
    public void setCharge(Byte charge) {
        this.charge = charge;
    }
}