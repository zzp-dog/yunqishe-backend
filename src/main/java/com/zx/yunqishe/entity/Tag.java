package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Table(name="tag")
public class Tag {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型: 1-用户标签，2-话题标签，3-媒体标签
     */
    private Byte type;

    /**
     * 对象id，给谁贴标签
     */
    private Integer oid;
}
