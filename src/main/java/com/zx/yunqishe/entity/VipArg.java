package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 会员时长参数配置表
 */
@Getter
@Setter
public class VipArg {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员类型1-普通会员，2-超级会员（目前暂不开放）
     */
    @NotNull@Min(1)@Max(2)
    private Byte type;

    /**
     * 持续时长，单位月，100*12时表示永久
     */
    @NotNull@Min(1)@Max(100*12)
    private Integer duration;

    /**
     * 开通会员时支付多少元，与支付多少云币二选一
     */
    @NotNull@Min(1)
    private BigDecimal price;

    /**
     *开通会员时需支付多少云币，与支付多少元二选一
     */
    @NotNull@Min(1)
    private BigDecimal coin;
}
