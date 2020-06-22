package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.EmailDispose;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.EmailDisposeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 邮件设置参数配置 控制器
 */

@Validated
@RestController
@RequestMapping(API.EMAIL_DISPOSE)
public class EmailDisposeController {

    @Autowired
    private EmailDisposeService emailDisposeService;

    //////////////////////////backend////////////////////////////////

    /**
     * 后台查询邮件设置列表
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData uSelectList() {
        List<EmailDispose> emailDisposes = emailDisposeService.bSelectList();
        return ResponseData.success().add("emailDisposes", emailDisposes);
    }

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
    ) throws Exception {
        emailDisposeService.bBatchDelete(ids);
        return ResponseData.success();
    }

    /**
     * 根据传入的实体集合，有id则插入，无id则更新
     * @param emailDisposes - 实体集合
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.BATCH+API.INSERT_OR_UPDATE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bBatchInsertOrUpdate(
            @RequestBody @Valid List<EmailDispose> emailDisposes
    ) {
        emailDisposeService.bBatchInsertOrUpdate(emailDisposes);
        return ResponseData.success();
    }

}
