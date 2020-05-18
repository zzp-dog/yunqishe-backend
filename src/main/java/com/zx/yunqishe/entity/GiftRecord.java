package com.zx.yunqishe.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 礼物送出接收记录表
 */
@Getter
@Setter
@Table(name = "gift_record")
public class GiftRecord {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 接收方
     */
    private Integer uid1;

    /**
     * 送出方
     */
    private Integer uid2;

    /**
     * 送出数量
     */
    private Integer count;


    /**
     * 礼物id
     */
    private Integer gid;

    /**
     * 接收方是否已读该记录 0-否，1-已读
     */
    private Byte known;

    @JsonFormat(
            locale = "zh",
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    @Column(name = "send_time")
    private Date sendTime;

    /**
     * 关联表gift表
     */
    @Transient
    private Gift gift;

    /**
     * 连查时统计收到的同种的礼物数目
     */
    @Transient
    private Integer groupCount;

}
