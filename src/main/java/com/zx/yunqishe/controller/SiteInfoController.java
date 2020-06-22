package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.SiteInfo;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.SiteInfoService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API.SITE_INFO)
public class SiteInfoController {

    @Autowired
    private SiteInfoService siteInfoService;

    /**
     * 后台查询站点信息
     * @return
     */
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND+API.SELECT+API.ONE)
    public ResponseData bSelectOne() {
        SiteInfo siteInfo = siteInfoService.selectSiteInfo();
        return ResponseData.success().add("siteInfo", siteInfo);
    }

    /**
     * 后台更新站点信息
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"superAdmin"})
    @RequestMapping(API.BACKEND+API.UPDATE+API.ONE)
    public ResponseData bUpdateOne(@RequestBody SiteInfo siteInfo) {
        siteInfoService.updateOne(siteInfo);
        return ResponseData.success();
    }

}
