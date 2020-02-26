package com.zx.yunqishe.controller.role;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.Role;
import com.zx.yunqishe.entity.extral.res.SimplePower;
import com.zx.yunqishe.service.power.PowerService;
import com.zx.yunqishe.service.role.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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
    @RequiresPermissions("rbac:role:select")
    @GetMapping(API.SELECT + API.LIST)
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
    @RequiresPermissions("rbac:role:delete")
    @PostMapping(API.DELETE)
    public ResponseData roleDelete(@NotEmpty @RequestBody List<Integer> ids){
        return roleService.batchDelete(ids);
    }
}
