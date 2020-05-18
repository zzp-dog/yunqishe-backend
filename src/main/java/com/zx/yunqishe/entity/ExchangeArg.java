package com.zx.yunqishe.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Table(name = "exchange_arg")
@Getter
@Setter
public class ExchangeArg {
    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 多少元
     */
    @NotNull
    @Min(1)
    private BigDecimal price;

    /**
     * price多少元可以兑换的云币数量
     */
    @NotNull@Min(1)
    private BigDecimal coin;
}
