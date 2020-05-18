package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.ChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API.CHARGE)
public class ChargeController {

    @Autowired
    private ChargeService chargeService;

    /**
     * 查询是否存在支付记录
     * @param id - 付费内容id
     * @param type - 付费内容类型
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.COUNT)
    public ResponseData fSelectOne(
            @RequestParam(name = "id")Integer id,
            @RequestParam(name = "type")Byte type
    ) {
        Integer count = chargeService.fSelectCount(id, type);
        return ResponseData.success().add("result", count);
    }
}
