package com.zx.yunqishe.entity;

import javax.persistence.*;

@Table(name = "concern")
public class Concern {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id-关注方
     */
    private Integer uid;

    /**
     * 被关注方
     */
    private Integer oid;

    /**
     * 1-关注，0-不关注
     */
    private Byte concern;

    /**
     * 关注或收藏得是
     * 1-话题内容，2-话题，3-用户
     */
    private Byte type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Byte getConcern() {
        return concern;
    }

    public void setConcern(Byte concern) {
        this.concern = concern;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}