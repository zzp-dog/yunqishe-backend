package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class Thumb {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 话题内容或评论id
     */
    private Integer oid;

    /**
     * 1-点赞，2-鄙视，3-无状态
     */
    private Byte thumb;
    /**
     * 1-话题内容，2-话题内容回复，3-媒体内容，4-媒体内容回复，5-用户
     */
    @NotNull
    private Byte type;
}