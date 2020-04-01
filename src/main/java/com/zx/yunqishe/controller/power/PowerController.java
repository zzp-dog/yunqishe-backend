package com.zx.yunqishe.controller.power;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.extral.res.SimplePower;
import com.zx.yunqishe.service.power.PowerService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 只允许超级管理进行操作
 */
@RestController
@RequestMapping(API.POWER)
@Validated
public class PowerController {
    @Autowired
    private PowerService powerService;
    /**
     * 查询所有权限[一般在一页就能显示，故不作分页]
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT+API.LIST)
    @RequiresPermissions("rbac:power:select")
    public ResponseData powerSelectList() {
        List<Power> powers = powerService.selectAll();
        return ResponseData.success().add("powers", powers);
    }

    /**
     * 查询简要权限列表
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT + API.SIMPLE + API.LIST)
    @RequiresPermissions("rbac:power:select")
    public ResponseData powerSelectSimpleList() {
        List<SimplePower> simplePowers = powerService.selectSimpleAll();
        return ResponseData.success().add("simplePowers", simplePowers);
    }

    /**
     * 根据id查某个权限实体
     * @param id
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT)
    @RequiresPermissions("rbac:power:select")
    public ResponseData powerSelect(@RequestParam Integer id) {
        Power power = powerService.powerSelect(id);
        return ResponseData.success().add("power", power);
    }

    /**
     * 插入权限
     * @param power 权限实体
     * @return
     */
    @Decrypt
    @PostMapping(API.INSERT)
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:power:insert")
    public ResponseData powerInsert(@Valid @RequestBody Power power) {
        return powerService.powerInsert(power);
    }

    /**
     * 根据权限id更新权限
     * @param power 权限实体
     * @return
     */
    @Decrypt
    @PostMapping(API.UPDATE)
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:power:update")
    public ResponseData powerUpdate(@Valid @RequestBody Power power) {
        return powerService.powerUpdate(power);
    }

    /**
     * 单个删除和批量删除
     * @param ids 权限id数组
     * @return
     */
    @Decrypt
    @PostMapping(API.DELETE)
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:power:delete")
    public ResponseData powerDelete(@RequestBody List<Integer> ids) {
        return powerService.powerDelete(ids);
    }

}
