package com.zx.yunqishe.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.consts.RegExp;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.entity.request.SingleUser;
import com.zx.yunqishe.service.UserService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
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
     * 前台登录
     * @param user
     * @return
     */
    @Decrypt
    @RequestMapping(API.LOGIN)
    public ResponseData login(@RequestBody @Valid SingleUser user) {return userService.login(user);}

    /**
     * 后台登录
     * @param user
     * @return
     */
    @Decrypt
    @RequestMapping(API.BACKEND + API.LOGIN)
    public ResponseData bLogin(@RequestBody @Valid SingleUser user) {return userService.bLogin(user);}

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
            @Pattern(regexp= RegExp.VERIFY_CODE, message = ErrorMsg.CODE_ERROR) String code, HttpServletRequest req
    ) {
        return userService.verifyCode(code, req);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Decrypt
    @PostMapping(API.REGIST)
    public ResponseData regist(@RequestBody @Valid User user, HttpServletRequest req) {
        Integer status = (Integer)req.getSession().getAttribute("codeStatus");
        if (1 != status) { // 验证码未通过验证
            req.getSession().removeAttribute("codeStatus");
            return ResponseData.error(ErrorMsg.CODE_STATUS_ERROR);
        }
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
    @RequestMapping(value = API.SETUP, method = {RequestMethod.GET, RequestMethod.POST})
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
    @GetMapping(API.SELECT+API.LIST)
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
        map.put("name",name!=null?"%"+name+"%":null);
        map.put("nickname",nickname!=null?"%"+nickname+"%":nickname);

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
    @GetMapping(API.SELECT+API.ONE)
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
    @PostMapping(API.BACKEND+API.UPDATE+API.ONE)
    @RequiresUser
    public ResponseData userBackendUpdateOne(@RequestBody User admin, HttpServletRequest req) {
        return userService.updateAdminBaseInfo(admin, req);
    }

    /**
     * 首页查询用户列表（只包含id，性别，头像，昵称，经验）
     * @param sex - -1-全部，1-男，2-女，3-未知
     * @param type - -1-全部，1- 活跃，2-新人，3-随机
     * @param pageNum 哪一页
     * @param pageSize 页大小
     * @return
     */
    @GetMapping(API.FRONTEND + API.SELECT + API.LIST)
    public ResponseData fSelectList(
            @RequestParam(name = "sex", required = false) Byte sex,
            @RequestParam(name = "type", required = false) Byte type,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userService.fSelectList(sex, type);
        PageInfo pageInfo = new PageInfo<User>(users);
        return ResponseData.success().add("users", pageInfo.getList());
    }

    /**
     * 查询活跃用户
     * @param pageSize
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT  +"/active"  +API.LIST)
    public ResponseData fSelectActiveList(@RequestParam(name = "pageSize", defaultValue = "15") Integer pageSize) {
        return userService.fSelectActiveList(pageSize);
    }

    /**
     * 前台根据用户id查用户详情信息
     * @param id - 用户id
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+"/detail"+API.ONE)
    public ResponseData fSelectDetailOne(
            @RequestParam(name = "id") Integer id
    ) {
        return userService.fSelectDetailOne(id);
    }

    /**
     * 根据用户id和动态类型查动态
     * @param id - 用户id
     * @param type - 动态类型 1-全部，2-圈子，3-问答，4-相册，5-音乐，6-视频
     * @param pageNum - 哪一页
     * @param pageSize - 分页大小
     * @return
     */
    @GetMapping(API.FRONTEND+ API.SELECT+"/dynamic"+API.LIST)
    public ResponseData fSelectDynamicList(
            @RequestParam(name = "id")Integer id,
            @RequestParam(name = "type")Byte type,
            @RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10")Integer pageSize
    ){
        return userService.fSelectDynamicList(id, type, pageNum, pageSize);
    }

    /**
     * 前台更新用户资料
     * @param user
     * @return
     */
    @Decrypt
    @PostMapping(API.FRONTEND+API.UPDATE+API.ONE)
    @RequiresUser
    public ResponseData fUpdateOne(@RequestBody User user) {
        userService.fUpdateOne(user);
        return ResponseData.success();
    }

    /**
     * 前台更新用户个性化信息
     * @param user
     * @return
     */
    @Decrypt
    @PostMapping(API.FRONTEND+API.UPDATE+"/personalizeInfo")
    @RequiresUser
    public ResponseData fUpdatePersonalizeInfo(@RequestBody User user) {
        userService.fUpdatePersonalizeInfo(user);
        return ResponseData.success();
    }
}
