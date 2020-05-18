package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.Role;
import com.zx.yunqishe.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 只允许超级管理进行操作
 */
@RestController
@Validated
@RequestMapping(API.ROLE)
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 查角色列表
     * @return
     */
    @Encrypt
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:role:select")
    @GetMapping(API.SELECT+API.LIST)
    public ResponseData roleSelectList() {
        return roleService.roleSelectList();
    }

    /**
     * 其实根据角色id查询角色
     * 但这里用role实体，不另外建vo
     * @param id
     * @return
     */
    @Encrypt
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:role:select")
    @GetMapping(API.SELECT)
    public ResponseData roleSelect(@RequestParam Integer id) {
        Role role = roleService.selectRoleWithPowers(id);
        return ResponseData.success().add("role", role);
    }

    /**
     * 插入角色
     * @param role
     * @return
     */
    @Decrypt
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:role:insert")
    @PostMapping(API.INSERT)
    public ResponseData roleInsert(@RequestBody @Valid Role role) {
        return  roleService.insertRole(role);
    }

    /**
     * 根据id更新角色
     * @param role
     * @return
     */
    @Decrypt
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:role:update")
    @PostMapping(API.UPDATE)
    public ResponseData roleUpdate(@RequestBody @Valid Role role) {
        return roleService.updateRole(role);
    }

    /**
     * 单个和批量删除
     * @param ids
     * @return
     */
    @Decrypt
    @RequiresRoles("superAdmin")
    @RequiresPermissions("rbac:role:delete")
    @PostMapping(API.DELETE)
    public ResponseData roleDelete(@NotEmpty @RequestBody List<Integer> ids){
        return roleService.batchDelete(ids);
    }
}
