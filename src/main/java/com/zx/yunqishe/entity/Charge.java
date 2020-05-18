package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
public class Charge {

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id-付费方
     */
    private Integer uid1;

    /**
     * 用户id-收费方
     */
    private Integer uid2;

    /**
     * 收费方id
     */
    private Integer oid;

    /**
     * 付费多少云币，全价时必为偶数，变价即为奇数
     */
    private BigDecimal price;

    /**
     * 收费对象类型
     1-话题内容，2-纯媒体内容，3-文档，4-用户
     */
    private Byte type;

}
