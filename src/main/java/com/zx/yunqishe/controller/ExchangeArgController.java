package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.ExchangeArg;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.ExchangeArgService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 云币兑换控制器
 */

@Validated
@RestController
@RequestMapping(API.EXCHANGE_ARG)
public class ExchangeArgController {
    @Autowired
    private ExchangeArgService exchangeArgService;

    //////////////////////////universal/////////////////////////////

    /**
     * 前台和后台查询列表
     * @return
     */
    @GetMapping(API.UNIVERSAL+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData uSelectList() {
        List<ExchangeArg> exchangeArgs = exchangeArgService.uSelectList();
        return ResponseData.success().add("exchangeArgs", exchangeArgs);
    }

    //////////////////////////backend////////////////////////////////

    /**
     * 根据id删除
     * @param ids - 单个id或多个id
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.BATCH+API.DELETE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bBatchDelete(
            @RequestBody @NotEmpty List<Integer> ids
    ) {
        exchangeArgService.bBatchDelete(ids);
        return ResponseData.success();
    }

    /**
     * 根据传入的实体集合，有id则插入，无id则更新
     * @param exchangeArgs - 实体集合
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.BATCH+API.INSERT_OR_UPDATE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bBatchInsertOrUpdate(
            @RequestBody @Valid List<ExchangeArg> exchangeArgs
    ) {
        exchangeArgService.bBatchInsertOrUpdate(exchangeArgs);
        return ResponseData.success();
    }

    /////////////////////////////FRONTEND/////////////////////////////////
}
