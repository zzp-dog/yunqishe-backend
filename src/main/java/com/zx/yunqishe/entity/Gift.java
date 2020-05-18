package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "gift")
public class Gift {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Byte id;

    /**
     * 所需云币
     */
    private BigDecimal price;

    /**
     * 链接
     */
    private String url;

    /**
     * 1-vip可买，0-只需金币
     */
    private Byte vip;

    /**
     * 礼物名称
     */
    private String name;

    /**
     * 单个魅力值
     */
    @Column(name = "charm_value")
    private Integer charmValue;

    /**
     * 描述
     */
    private String description;
}
