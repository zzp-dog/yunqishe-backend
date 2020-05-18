package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "concern")
@Getter
@Setter
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
     * 关注或收藏
     * 1-话题内容，2-问云话题，3-圈子话题,4-媒体，5-媒体内容，6用户
     */
    private Byte type;

}