package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import javax.persistence.*;

@Getter
@Setter
@Table(name = "site_info")
public class SiteInfo {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 站长
     */
    private String master;

    /**
     *  站长QQ
     */
    @Column(name="master_qq")
    private String masterQQ;

    /**
     *  站长微信
     */
    @Column(name="master_wx")
    private String masterWX;

    /**
     * 网站名称
     */
    @Column(name = "site_name")
    private String siteName;

    /**
     * ICP 备案号
     */
    @Column(name = "icp")
    private String ICP;

    /**
     * 存活天数
     */
    @Column(name = "keep_days")
    private Integer keepDays;

    /**
     * 版本号
     */
    private BigDecimal version;

}