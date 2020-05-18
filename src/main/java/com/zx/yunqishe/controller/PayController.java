package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.Pay;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(API.PAY)
public class PayController {

    @Autowired
    private PayService payService;

    /**
     * 单一插入支付记录表
     * @param pay
     * @return
     */
    @Decrypt
    @PostMapping(API.FRONTEND+API.INSERT+API.ONE)
    public ResponseData fInsertOne(
            @RequestBody @Valid Pay pay,
            @SessionAttribute("me") User me
    ){
        return payService.fInsertOne(pay, me);
    }
}
