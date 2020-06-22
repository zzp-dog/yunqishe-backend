package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="image_dispose")
@Getter
@Setter
public class ImageDispose {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    /**
     * 存储链接
     */
    private String url;

    /** 跳转链接 */
    private String href;
    /** 所属类型:1-首页,2-圈子,3-微媒体 */
    private Byte type;
    /** 描述（可选） */
    private String description;
}
