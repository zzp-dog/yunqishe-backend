package com.zx.yunqishe.entity;

import javax.persistence.*;

public class Evaluate {
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
    private Integer cid;

    /**
     * 1-收藏，0-不收藏
     */
    private Byte collect;

    /**
     * 1-喜欢，0-不喜欢
     */
    private Byte love;

    /**
     * 1-点赞，0-鄙视，-1-无状态
     */
    private Byte thumbs;

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
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 获取话题内容或评论id
     *
     * @return cid - 话题内容或评论id
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * 设置话题内容或评论id
     *
     * @param cid 话题内容或评论id
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * 获取1-收藏，0-不收藏
     *
     * @return collect - 1-收藏，0-不收藏
     */
    public Byte getCollect() {
        return collect;
    }

    /**
     * 设置1-收藏，0-不收藏
     *
     * @param collect 1-收藏，0-不收藏
     */
    public void setCollect(Byte collect) {
        this.collect = collect;
    }

    /**
     * 获取1-喜欢，0-不喜欢
     *
     * @return love - 1-喜欢，0-不喜欢
     */
    public Byte getLove() {
        return love;
    }

    /**
     * 设置1-喜欢，0-不喜欢
     *
     * @param love 1-喜欢，0-不喜欢
     */
    public void setLove(Byte love) {
        this.love = love;
    }

    /**
     * 获取1-点赞，0-鄙视，-1-无状态
     *
     * @return thumbs - 1-点赞，0-鄙视，-1-无状态
     */
    public Byte getThumbs() {
        return thumbs;
    }

    /**
     * 设置1-点赞，0-鄙视，-1-无状态
     *
     * @param thumbs 1-点赞，0-鄙视，-1-无状态
     */
    public void setThumbs(Byte thumbs) {
        this.thumbs = thumbs;
    }
}