package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.cache.AppCache;
import com.zx.yunqishe.entity.Switch;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.SwitchService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API.SWITCH)
public class SwitchController {

    @Autowired
    private SwitchService switchService;

    /**
     * 前台查询开关
     * @return
     */
    @RequiresRoles(value = {"admin", "superAdmin"})
    @RequestMapping(API.BACKEND+API.SELECT+API.ONE)
    public ResponseData bSelectOne() {
        Switch mySwitch = AppCache.mySwitch;
        return ResponseData.success().add("switch", mySwitch);
    }

    /**
     * 后台更新开关
     * @param mySwitch
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"admin", "superAdmin"})
    @RequestMapping(API.BACKEND+API.UPDATE+API.ONE)
    public ResponseData bUpdateOne(@RequestBody Switch mySwitch) {
        AppCache.mySwitch = mySwitch;
        switchService.updateOne(mySwitch);
        return ResponseData.success();
    }
}
