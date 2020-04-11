package com.zx.yunqishe.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.consts.RegExp;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.entity.extral.req.SingleUser;
import com.zx.yunqishe.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(API.USER)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户是否已登录或记住我
     * @return
     */
    @RequestMapping(API.IS_RECORD)
    public ResponseData isRecord(){
        return userService.isRecord();
    }

    /**
     * 登录
     * @param suser
     * @return
     */
    @Decrypt
    @PostMapping(API.LOGIN)
    public ResponseData login(@RequestBody @Valid SingleUser suser) {
        return userService.login(suser);
    }

    /**
     * 发送验证码
     * @param email
     * @return
     */
    @Decrypt
    @PostMapping(API.SEND_CODE)
    public ResponseData sendCode(@RequestBody @Email @NotBlank String email, HttpServletRequest req) throws Exception{
        return userService.sendCode(email, req);
    }

    /**
     * 验证验证码
     * @param code
     * @param req
     * @return
     */
    @Decrypt
    @PostMapping(API.VERIFY_CODE)
    public ResponseData verifyCode(
            @RequestBody
            @Pattern(regexp= RegExp.VERIFY_CODE, message = ErrorMsg.CODE_ERROR) String code, HttpServletRequest req) {
        return userService.verifyCode(code, req);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Decrypt
    @PostMapping(API.REGIST)
    public ResponseData regist(@RequestBody @Valid User user) {
        // rid：6 - 普通用户
        ResponseData rd = userService.regist(user, 6);
        if (rd.getStatus() == 400) return rd;
        // 注册成功后去请求登录api
        SingleUser singleUser = new SingleUser();
        singleUser.setAccount(user.getAccount());
        singleUser.setPassword(user.getPassword());
        return login(singleUser);
    }

    /**
     * 登出，client直接请求
     * @return
     */
    @GetMapping(API.LOGOUT)
    public ResponseData logout(HttpServletResponse res) {
        return userService.logout(res);
    }

    /**
     * 查询是否已经存在系统管理员
     * @return
     */
    @RequestMapping(value = API.QUERY_ADMIN, method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData queryAdmin() {
        return userService.qeuryAdmin();
    }

    /**
     * 安装
     * @return
     */
    @Decrypt
    @PostMapping(API.INSTALL)
    public ResponseData install(@RequestBody @Valid User user) {
        ResponseData rd = userService.install(user);
        if (rd.getStatus() == 400) return rd;
        // 安装成功去请求登录api
        SingleUser singleUser = new SingleUser();
        singleUser.setAccount(user.getAccount());
        singleUser.setPassword(user.getPassword());
        return login(singleUser);
    }

    /**
     * 查询简要用户信息列表,分页
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT+ API.LIST)
    @RequiresPermissions("rbac:user:select")
    public ResponseData selectUsersWithRolesByConditions(@RequestParam(defaultValue = "1") Integer pageNum,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer sex,
                                       @RequestParam(required = false) String nickname,
                                       @RequestParam(required = false) Integer status,
                                       @RequestParam(required = false) Integer roleId) {
        Map<String, Object> map = new HashMap(8);
        map.put("sex",sex);
        map.put("status",status);
        map.put("roleId",roleId);
        map.put("name",name!=null?"%" + name + "%":null);
        map.put("nickname",nickname!=null?"%" + nickname+"%":nickname);

        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.selectUsersWithRolesByConditions(map);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return ResponseData.success().add("pageInfo", pageInfo);
    }

    /**
     * 查询某个用户信息，除了一些私密信息，如password等
     * 必须是应用的用户才能访问
     * @return
     */
    @Encrypt
    @RequiresUser
    @GetMapping(API.SELECT + API.ONE)
    public ResponseData userSelectOne(@RequestParam Integer id) {
        User user = userService.userSelectOne(id);
        return ResponseData.success().add("user",user);
    }

    /**
     * 插入用户
     * @param user
     * @return
     */
    @Decrypt
    @RequiresPermissions("rbac:user:insert")
    @PostMapping(API.INSERT)
    public ResponseData userInsert(@RequestBody @Valid User user) {
        return userService.userInsert(user);
    }

    /// 后面两个仅限具有对应操作权限的超级管理和普通管理 有效！！！
    /**
     * 单个或批量删除用户到回收站，仅限管理员访问
     * @param ids
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"admin", "superAdmin"}, logical = Logical.OR)
    @RequiresPermissions("rbac:user:delete")
    @PostMapping(API.BATCH+API.TRASH)
    public ResponseData userBatchTrash(@RequestBody List<Integer> ids) {
        return userService.userBatchTrash(ids);
    }

    /**
     * 非内置账号status：4时，只能单个或批量更新用户角色和状态
     * 其他不能更改，否则算作侵犯隐私
     * @param users
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"admin", "superAdmin"}, logical = Logical.OR)
    @RequiresPermissions("rbac:user:update")
    @PostMapping(API.BATCH+API.UPDATE)
    public ResponseData userBatchUpdate(@RequestBody List<User> users) {
        return userService.userBatchUpdate(users);
    }

    /**
     * 管理员的资料修改
     * @param admin
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.UPDATE + API.ONE)
    @RequiresUser
    public ResponseData userBackendUpdateOne(@RequestBody User admin, HttpServletRequest req) {
        return userService.updateAdminBaseInfo(admin, req);
    }

    /**
     * 查询活跃用户
     * @param pageSize
     * @return
     */
    @GetMapping(API.FRONTEND + API.SELECT  + "/active"  + API.LIST)
    public ResponseData fSelectActiveList(@RequestParam(name = "pageSize", defaultValue = "15") Integer pageSize) {
        return userService.fSelectActiveList(pageSize);
    }
}
