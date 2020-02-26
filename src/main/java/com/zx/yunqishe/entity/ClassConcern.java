package com.zx.yunqishe.entity;

import javax.persistence.*;

@Table(name = "class_concern")
public class ClassConcern {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id-关注方
     */
    private Integer uid1;

    /**
     * 用户id-被关注方
     */
    private Integer uid2;

    /**
     * 1-关注，0-不关注
     */
    private Byte concern;

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
     * 获取用户id-关注方
     *
     * @return uid1 - 用户id-关注方
     */
    public Integer getUid1() {
        return uid1;
    }

    /**
     * 设置用户id-关注方
     *
     * @param uid1 用户id-关注方
     */
    public void setUid1(Integer uid1) {
        this.uid1 = uid1;
    }

    /**
     * 获取用户id-被关注方
     *
     * @return uid2 - 用户id-被关注方
     */
    public Integer getUid2() {
        return uid2;
    }

    /**
     * 设置用户id-被关注方
     *
     * @param uid2 用户id-被关注方
     */
    public void setUid2(Integer uid2) {
        this.uid2 = uid2;
    }

    /**
     * 获取1-关注，0-不关注
     *
     * @return concern - 1-关注，0-不关注
     */
    public Byte getConcern() {
        return concern;
    }

    /**
     * 设置1-关注，0-不关注
     *
     * @param concern 1-关注，0-不关注
     */
    public void setConcern(Byte concern) {
        this.concern = concern;
    }
}