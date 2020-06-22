package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 开关操作
 */
@Getter
@Setter
public class Switch {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 1-可注册，0-不可注册
     */
    @Column(name = "sign_up")
    private Byte signUp;

    /**
     * 1-可登录，0-不可登录
     */
    @Column(name = "sign_in")
    private Byte signIn;

    /**
     * 1- 可发表， 0-不可发表
     */
    private Byte publish;

    /**
     * 1-可关注/收藏，0-不可关注/收藏
     */
    private Byte concern;

    /**
     * 1-可评论， 0-不可评论
     */
    private Byte comment;

    /**
     * 1-可点赞/反对，0-不可点赞/反对
     */
    private Byte thumb;

}
