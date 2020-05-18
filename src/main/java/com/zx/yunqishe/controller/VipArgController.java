package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.VipArg;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.VipArgService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 会员时长相关参数配置 控制器
 */

@Validated
@RestController
@RequestMapping(API.VIP_ARG)
public class VipArgController {

    @Autowired
    private VipArgService vipArgService;
    //////////////////////////universal/////////////////////////////

    /**
     * 前台和后台根据会员类型查询列表,type不为1和2时查所有
     * @param type - 会员类型1-vip，2-svip
     * @return
     */
    @GetMapping(API.UNIVERSAL+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData uSelectList(
            @RequestParam(name = "type") Byte type
    ) {
        List<VipArg> vipArgs = vipArgService.uSelectList(type);
        return ResponseData.success().add("vipArgs", vipArgs);
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
        vipArgService.bBatchDelete(ids);
        return ResponseData.success();
    }

    /**
     * 根据传入的实体集合，有id则插入，无id则更新
     * @param vipArgs - 实体集合
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.BATCH+API.INSERT_OR_UPDATE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bBatchInsertOrUpdate(
            @RequestBody @Valid List<VipArg> vipArgs
    ) {
        vipArgService.bBatchInsertOrUpdate(vipArgs);
        return ResponseData.success();
    }

    /////////////////////////////FRONTEND/////////////////////////////////
}
