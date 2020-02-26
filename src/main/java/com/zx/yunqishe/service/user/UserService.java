package com.zx.yunqishe.service.user;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.utils.DateUtil;
import com.zx.yunqishe.common.utils.Generator;
import com.zx.yunqishe.common.utils.email.EmailUtil;
import com.zx.yunqishe.common.utils.email.SendEmail;
import com.zx.yunqishe.dao.RoleMapper;
import com.zx.yunqishe.dao.UserMapper;
import com.zx.yunqishe.dao.UserRoleMapper;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.Role;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.entity.UserRole;
import com.zx.yunqishe.entity.extral.req.SingleUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    // 创建任务计划线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);


    /**
     * 注册
     * @param user
     */
    public ResponseData regist(User user, Integer rid) {
        // 插入用户
        // 1.根据账号查用户
        String account  = user.getAccount();
        User count_user = new User();
        count_user.setAccount(account);
        int count = userMapper.selectCount(count_user);
        // 用户是否已存在
        if (count > 0) {
            return ResponseData.error(ErrorMsg.ACCOUNT_EXIST_ERROR);
        }
        // 邮箱是否已占用
        User e_count_user = new User();
        e_count_user.setEmail(user.getEmail());
        int e_count = userMapper.selectCount(e_count_user);
        // 邮箱是否已存在
        if (e_count > 0) {
            return ResponseData.error(ErrorMsg.EMAIL_EXIST_ERROR);
        }
        // 2.插入用户
        // 设置随机昵称
        user.setNickname(Generator.getRandomStr(6));
        Integer id = userMapper.insertSelective(user);
        UserRole userRole = new UserRole();
        // 3.设为普通用户
        userRole.setUserId(id);
        userRole.setRoleId(rid);
        userRoleMapper.insertSelective(userRole);
        return ResponseData.success();
    }

    /**
     * 登录
     * @param suser
     * @return
     * @throws Exception
     */
    public ResponseData login(SingleUser suser) {
        String name = suser.getAccount();
        String pass = suser.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name,pass);
        // 执行认证登陆并记住我
        try {
            token.setRememberMe(true);
            subject.login(token);
            return ResponseData.success();
        } catch (Exception uae) {
            return ResponseData.error(ErrorMsg.LOGIN_ERROR);
        }
    }

    /**
     * 登出
     * @return
     * @throws Exception
     */
    public ResponseData logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return ResponseData.success().add("result" ,1);
    }

    /**
     * 查询是否存在超级管理
     * @return
     */
    public ResponseData qeuryAdmin() {
        User user = new User();
        // 超级管理员rid默认为1
        user.setRoleId(1);
        int count = userMapper.selectCount(user);

        if (count > 0) {
            return ResponseData.error(ErrorMsg.ADMIN_EXISTS_ERROR);
        }

        return ResponseData.success();
    }

    /**
     * 安装,安装后不自动调登录接口，需要到登录界面调登录接口
     * @param user
     */
    public ResponseData install(User user) {
        // 查询是否存在超级管理
        ResponseData rd = this.qeuryAdmin();
        if (rd.getStatus() == 400) return rd;
        return regist(user, 1);
    }

    /**
     * 发送验证码，不进行数据库操作
     * @param email
     * @param req
     * @return
     */
    public ResponseData sendCode(String email, HttpServletRequest req) throws Exception{
        // 生成验证码
        String code = Generator.getRandomNum(6);

        // 用callable线程发送验证码
        SendEmail sendEmail = new SendEmail();
        sendEmail.setTo(email);
        sendEmail.setCode(code);
        sendEmail.setType("code");
        sendEmail.setHost("smtp.qq.com");
        sendEmail.setAuth("wohyfofluhtybdgb");
        sendEmail.setFrom("1029512956@qq.com");
        sendEmail.setSendTimeout(4000L); // 设置超时4s

        // 是否发送成功
        Boolean b = new EmailUtil(sendEmail).SyncSend();
        if (!b) {
            return ResponseData.error(ErrorMsg.SEND_EMAIL_ERROR);
        }

        // 存在session
        String timeStamp = DateUtil.timeStamp(null).toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", code);
        map.put("timeStamp", timeStamp);
        HttpSession session = req.getSession();
        session.setAttribute("codeMap", map);

        //清除时根据时间戳判断是不是这个任务对应的验证码
        scheduledExecutorService.schedule(new Thread(()->{
            Map<String, String> codeMapS = (Map<String, String>) session.getAttribute("codeMap");
            if(Objects.nonNull(codeMapS) && timeStamp.equals(codeMapS.get("timeStamp"))){
                session.removeAttribute("codeMap");
            }
        }), 60, TimeUnit.SECONDS);

        return ResponseData.success();
    }

    /**
     * 验证验证码，不进行数据库操作
     * @param code
     * @param req
     * @return
     */
    public ResponseData verifyCode(String code, HttpServletRequest req) {
        // 获取session里的验证码
        HttpSession session = req.getSession();
        Map<String, String> map = (Map<String, String>) session.getAttribute("codeMap");

        Integer status = 1; // 默认成功
        if (map == null) { // 超时或未发送验证码
            status = 3;
        } else if (!map.get("code").equals(code)) { // 验证码错误
            status = 2;
        }

        // 验证成功销毁验证码
        if (status == 1) {
            session.removeAttribute("codeMap");
        }
        return ResponseData.success().add("status", status);
    }

    /**
     * 根据账号找用户
     * @param account
     * @return
     */
    public User getUserRolePower(String account) {
        // 根据账号查所有角色和所有权限

        // 因为数据库角色和权限都设计了父子结构，前端会展示成两棵树，
        // 所以限制了以下两点，要么只有父，要么只有子
        // 限制1：用户分配的角色不能有既有父角色，又有子角色，否则角色分配有问题
        // 限制2：角色分配的权限不能既有父权限，又有子权限，否则权限分配也有问题
        // 所以只要同通过5表左连接查询就能查到用户所有角色和权限
        // 注意：为角色分配权限的时候，要遵循父角色的权限大于子角色权限这个规则。
        return userMapper.getUserRolePower(account);
    }

    /**
     * 根据账号查询用户信息
     * @param account
     * @return
     */
    public User queryUserByAccount(String account) {
        User user = new User();
        user.setAccount(account);
        return userMapper.selectOne(user);
    }

    /**
     * 查询简单用户信息列表
     * @return
     * @param map
     */
    public List<User> userSelectList(Map<String, Object> map) {
        return userMapper.userSelectList(map);
    }

    /**
     * 查询某个用户的信息带角色
     * @return
     * @param id
     */
    public User userSelectOne(Integer id) {
        return userMapper.selectOneByPrimary(id);
    }

    /**
     * 单个或批量删除用户到回收站
     * @param ids
     * @return
     */
    public ResponseData userBatchTrash(List<Integer> ids) {
        User user  = (User)SecurityUtils.getSubject().getSession().getAttribute("me");
        Integer id = user.getId();
        // 1.普通管理不可回收超级管理,只有这两个角色才能删除用户
        if(ids.contains(1) && id == 2) {
            return ResponseData.error(ErrorMsg.CANNOT_TRASH);
        }
        // 2.回收，实际上是批量更新status,兼容批量更新sql
        List<User> users = new ArrayList<User>(ids.size());
        for (Integer temp : ids) {
            User u = new User();
            u.setId(temp);
            u.setStatus((byte)4);
            users.add(u);
        }
        userMapper.batchUpdate(users);
        return ResponseData.success();
    }

    /**
     * 批量更新用户的角色和状态
     * @param users
     * @return
     */
    public ResponseData userBatchUpdate(List<User> users) {
        User user  = (User)SecurityUtils.getSubject().getSession().getAttribute("me");
        Integer id = user.getId();
        // 获取uid数组链表
        List<Integer> ids = new ArrayList<>(users.size());
        for (User user1 : users) {
            ids.add(user1.getId());
        }
        /**
         * 有父级角色或平级角色则不可修改，
         * 目前只有超级管理和普通管理可修改
         * */
        if(!validUpdate(id,ids)) {
            return ResponseData.error(ErrorMsg.CANNOT_UPDATE);
        }
        // 删除用户的角色
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("userId",ids);
        userRoleMapper.deleteByExample(example);
        // 重新设置用户的角色
        userRoleMapper.batchInsert(users);
        // 对于非内置账号，确保只能修改状态和角色
        for (User user1 : users) {
            if (user1.getStatus() == 4){ // 内置账号可修改所有
                continue;
            }
            User user2 = new User();
            user2.setRoles(user1.getRoles());
            user2.setStatus(user1.getStatus());
            Integer index = users.indexOf(user1);
            users.set(index, user2);
        }
        userMapper.batchUpdate(users);
        return ResponseData.success();
    }

    /**
     * 判断是否修改的时子级角色
     * @param id
     * @param ids
     * @return true - 可修改，false-不可修改
     */
    private boolean validUpdate(Integer id, List<Integer> ids) {
        // 没有要更新的用户
        if(ids == null || ids.isEmpty()) return false;
        // 查询当前用户所有角色
        User self = userMapper.selectUserRoles(id);
        List<Role> sroles = self.getRoles();
        // 一次性连接查询出要修改的用户的用户角色集合
        List<User> users = userMapper.selectUserRolesIn(ids);
        // 查询所有角色
        List<Role> roles = roleMapper.selectAll();
        // 找当前用户的所有子角色,不包含本身的角色
        Map<Integer,Role> sallrolesMap = new HashMap<>();
        // 所有角色的map
        Map<Integer,Role> allrolesMap = new HashMap<>(roles.size());
        // 为所有角色建立map
        for (Role role : roles) {
            allrolesMap.put(role.getId(),role);
        }
        // 构建角色树或森林
        for (Role role : roles) {
            Integer pid = role.getPid();
            if(pid == -1)continue;
            Role p = allrolesMap.get(pid);
            if(p==null)continue;
            List<Role> list = p.getRoles();
            if(list == null){
                list = new ArrayList<>();
                p.setRoles(list);
            }
            list.add(role);
        }
        // 找当前用户所有子孙角色节点
        for (Role srole : sroles) {
            Integer rid = srole.getId();
            Role r = allrolesMap.get(rid);
            if (r == null) continue;
            getChilds(r, sallrolesMap);
        }
        // 判断是否子级角色
        boolean flag = true;
        for (User user : users) {
            List<Role> list = user.getRoles();
            if(list == null || list.isEmpty()) {
                continue;
            };
            for (Role role : list) {
                flag &= sallrolesMap.get(role.getId()) != null;
                if(!flag)return false;
            }
        }
        return flag;
    }

    /**
     * 找子孙角色
     * @param p
     * @param sallrolesMap
     */
    private void getChilds(Role p, Map<Integer, Role> sallrolesMap) {
        List<Role> roles = p.getRoles();
        if(roles == null || roles.isEmpty()){
            return;
        }
        for (Role role : roles) {
            sallrolesMap.put(role.getId(),role);
            getChilds(role, sallrolesMap);
        }
    }


    /**
     * 插入用户
     * @param user
     * @return
     */
    public ResponseData userInsert(User user) {
        userMapper.insertSelective(user);
        return ResponseData.success();
    }

    public static void main(String[] args) {
        List<?> list = new ArrayList<>();
        for (Object o : list) {
            System.out.println(o);
        }
    }
}
