package com.zx.yunqishe.service;

import com.github.pagehelper.PageHelper;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.cache.AppCache;
import com.zx.yunqishe.common.utils.CookieUtil;
import com.zx.yunqishe.common.utils.DateUtil;
import com.zx.yunqishe.common.utils.EmailUtil;
import com.zx.yunqishe.common.utils.Generator;
import com.zx.yunqishe.entity.EmailDispose;
import com.zx.yunqishe.dao.*;
import com.zx.yunqishe.entity.*;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.request.SingleUser;
import com.zx.yunqishe.entity.base.Content;
import com.zx.yunqishe.service.base.CommonService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService extends CommonService{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ConcernMapper concernMapper;

    @Autowired
    private GiftRecordMapper giftRecordMapper;

    @Autowired
    private TopicContentMapper topicContentMapper;

    @Autowired
    private ContentService contentService;

    @Autowired
    private TopicContentService topicContentService;

    @Autowired
    private MediaContentService mediaContentService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    // 创建任务计划线程池用于清除验证码
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
        String account = suser.getAccount();
        String password = suser.getPassword();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account,password);
        // 执行认证登陆并记住我
        try {
            token.setRememberMe(true);
            subject.login(token);
            // 更新登录时间
            LocalDateTime ldt = LocalDateTime.now();
            String now = DateUtil.dataTime2str(ldt);
            User user = new User();
            user.setLastLoginTime(now);
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("account", account);
            criteria.andEqualTo("password", password);
            userMapper.updateByExampleSelective(user, example);
            // 查用户简易信息并返回给前端
            User baseUser = getCurrentBaseUser();
            return ResponseData.success().add("user", baseUser);
        } catch (Exception e) {
            return ResponseData.error(ErrorMsg.LOGIN_ERROR).add("cause", e);
        }
    }

    /**
     * 后台登录(仅限管理员登录)
     * @param suser
     * @return
     */
    public ResponseData bLogin(SingleUser suser) {
        String account = suser.getAccount();
        User user = userMapper.selectBaseOneByAccount(account);
        List<String> roleNames = user.getRoleNames();
        if (!roleNames.contains("admin") || !roleNames.contains("superAdmin")) {
            return ResponseData.error(ErrorMsg.LOGIN_ERROR);
        }
        return this.login(suser);
    }

    /**
     * 登出
     * @return
     * @throws Exception
     */
    public ResponseData logout(HttpServletResponse res) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        // 清除cookie
        CookieUtil.removeCookie(res, "rememberMe");
        return ResponseData.success().add("result" ,1);
    }

    /**
     * 查询是否存在超级管理
     * @return
     */
    public ResponseData qeuryAdmin() {
        UserRole userRole = new UserRole();
        userRole.setRoleId(1); // 超级管理
        int count = userRoleMapper.selectCount(userRole);
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
        // 查验证码发送模板
        EmailTemplate emailTemplate = emailTemplateService.selectOneByType(1);
        Byte async = emailTemplate.getAsync();
        Integer expire = emailTemplate.getExpire();
        String subject = emailTemplate.getSubject();
        String template = emailTemplate.getTemplate();
        String content = template.replaceFirst("\\$1", code);
        // 配置发送邮件
        EmailDispose emailDispose = AppCache.emailDispose;
        emailDispose.setTo(email);
        emailDispose.setAsync(async);
        emailDispose.setSubject(subject);
        emailDispose.setContent(content);
        // 用callable线程发送验证码
        // 是否发送成功
        Object b = new EmailUtil(emailDispose).send();
        if (null != b && !(boolean)b) {
            // 同步发送不通过
            return ResponseData.error(ErrorMsg.SEND_EMAIL_ERROR);
        }
        // 该验证码对应的时间戳
        String timeStamp = DateUtil.timeStamp(null).toString();
        Map<String, String> map = new HashMap<String, String>();
        // 存在session里
        map.put("code", code);
        map.put("timeStamp", timeStamp);
        HttpSession session = req.getSession();
        session.setAttribute("codeMap", map);
        // 每次发送验证码都会产生一个计划任务
        //清除时根据时间戳判断是不是这个任务对应的验证码
        scheduledExecutorService.schedule(new Thread(()->{
            Map<String, String> codeMapS = (Map<String, String>) session.getAttribute("codeMap");
            if(Objects.nonNull(codeMapS) && timeStamp.equals(codeMapS.get("timeStamp"))){
                session.removeAttribute("codeMap");
            }
        }), expire, TimeUnit.MILLISECONDS);
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

        // 存贮验证状态
        session.setAttribute("codeStatus", status);
        return ResponseData.success().add("status", status);
    }

    /**
     * 根据账号找用户
     * @param account
     * @return
     */
    public User selectUserWithRolesWithPowers(String account) {
        return userMapper.selectUserWithRolesWithPowers(account);
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
     * 判断用户登陆状态
     * @return
     */
    public ResponseData isRecord() {
        Subject subject = SecurityUtils.getSubject();
        // 备注：已登录认证和记住我互斥
        if(!subject.isRemembered()&&!subject.isAuthenticated()){
            return ResponseData.error(ErrorMsg.TOKEN_ERROR);
        }

        if (subject.isRemembered()) { // 记住我需要重查用户把基本信息放到会话里
            User user = getCurrentBaseUser();
            subject.getSession().setAttribute("me", user);
        }
        User user = (User)(subject.getSession().getAttribute("me"));
        return ResponseData.success().add("user", user);
    }

    /**
     * 查询简单用户信息列表
     * @return
     * @param map
     */
    public List<User> selectUsersWithRolesByConditions(Map<String, Object> map) {
        return userMapper.selectUsersWithRolesByConditions(map);
    }

    /**
     * 查询某个用户的信息带角色
     * @return
     * @param id
     */
    public User userSelectOne(Integer id) {
        return userMapper.selectUserWithRolesByUserId(id);
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
            u.setStatus((byte)5); // 5 - 表示在回收站
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
        // 获取用户id数组链表
        List<Integer> ids = new ArrayList<>(users.size());
        for (User user1 : users) {
            ids.add(user1.getId());
        }
        /**
         * 只能修改为子角色
         * 目前只有超级管理和普通管理可修改
         * */
        if(!ids.isEmpty()&&!validUsersRole(ids)) {
            return ResponseData.error(ErrorMsg.ROLE_BOUND);
        }
        // 查询是否内置账号
        Example ex = new Example(User.class);
        Example.Criteria criteria = ex.createCriteria();
        criteria.andIn("id", ids);
        List<User> selectUsers = userMapper.selectByExample(ex);
        Map<Integer,User> userMap = new HashMap<>(selectUsers.size());
        for (User selectUser : selectUsers) {
            Integer id = selectUser.getId();
            userMap.put(id, selectUser);
        }
        // 内置账号可修改所有，其他账号只能改角色和状态
        for (User user : users) {
            User oneUser = userMap.get(user.getId());
            if(oneUser.getStatus() == 4) continue; // 内置账号全可修改
            User u = new User();
            u.setId(user.getId());
            u.setRoles(user.getRoles());
            u.setStatus(user.getStatus());
            Integer index = users.indexOf(user);
            users.set(index, u);
        }
        // 删除用户的角色
        Example example = new Example(UserRole.class);
        criteria = example.createCriteria();
        criteria.andIn("userId",ids);
        userRoleMapper.deleteByExample(example);
        // 重新设置用户的角色
        // 1.判断是否有角色，避免产生空sql
        // 2.判断时否有status，避免更新错误
        for (User user : users) {
            List<Role> roles = user.getRoles();
            if(roles == null || roles.isEmpty() ){
                Role r = new Role();
                r.setId(7); // 默认设置为游客
                roles = new ArrayList<>(1);
                roles.add(r);
                user.setRoles(roles);
            }
            if(user.getStatus() == null) {
                user.setStatus((byte)-1);
            }
        }
        userRoleMapper.batchInsert(users);
        // 更新用户信息，非内置只能更新状态
        userMapper.batchUpdate(users);
        return ResponseData.success();
    }

    /**
     * 插入用户
     * @param user
     * @return
     */
    public ResponseData userInsert(User user) {
        // 插入用户
        userMapper.insertSelective(user);
        // 看是否设置了角色
        List<Role> roles = user.getRoles();
        // 角色批量插入
        if(roles !=null && !roles.isEmpty()) {
            // 校验角色是否越界
            if(!validRole(roles)){
                return ResponseData.error(ErrorMsg.ROLE_BOUND);
            }
            List<User> users = new ArrayList<>();
            users.add(user);
            userRoleMapper.batchInsert(users);
        }
        // 返回操作成功
        return ResponseData.success();
    }

    /**
     * 校验设置的角色是否越界，
     * 针对单个用户的角色插入或修改
     * @param roles - 单个用户的批量角色
     * @return true - 是当前用户的子级角色，false-不是当前用户的子级角色
     */
    public boolean validRole(List<Role> roles) {
        Map<Integer, Role> map = getRolesMap();
        boolean flag = true;
        // 单个用户的批量角色检测
        for (Role role : roles) {
            flag &= map.get(role.getId()) != null;
            if(!flag) return false;
        }
        return true;
    }

    /**
     * 判断要修改的多个或单个用户的角色是否当前角色子级角色
     * 针对有id的用户的角色修改
     * @param ids 单个或批量用户的id
     * @return true - 是当前用户的子级角色，false - 不是当前用户的子级角色
     */
    private boolean validUsersRole(List<Integer> ids) {
        // 一次性连接查询出要修改的用户的用户角色集合
        List<User> users = userMapper.selectUserRolesUserIdIn(ids);
        Map<Integer,Role> map = getRolesMap();
        // 判断是否子级角色
        boolean flag = true;
        // 批量用户的的批量角色检测
        for (User user : users) {
            List<Role> list = user.getRoles();
            if(list == null || list.isEmpty()) {
                continue;
            };
            // 单个用户的多个角色
            for (Role role : list) {
                flag &= map.get(role.getId()) != null;
                if(!flag)return false;
            }
        }
        return true;
    }

    /**
     * 返回当前用户的所有子孙角色map
     * @return
     */
    private Map<Integer, Role> getRolesMap() {
        User currentUser  = (User)SecurityUtils.getSubject().getSession().getAttribute("me");
        Integer id = currentUser.getId();
        // 查询当前用户所有角色
        User self = userMapper.selectUserWithRolesByUserId(id);
        List<Role> selfRoles = self.getRoles();
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
        for (Role srole : selfRoles) {
            Integer rid = srole.getId();
            Role r = allrolesMap.get(rid);
            if (r == null) continue;
            getChilds(r, sallrolesMap);
        }
        return  sallrolesMap;
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
     * 更新管理员资料
     *
     * 这里就体现了tk.mybatis的不方便之处了，
     * 针对只准更新部分字段的场景，直接使用有选择的更新就不是那么安全了
     * 要在服务端重新封装实体或校验后传参，
     * 如果直接写死固定的sql则不会出现这种风险
     *
     * @param admin
     * @param req
     * @return
     */
    public ResponseData updateAdminBaseInfo(User admin, HttpServletRequest req) {
        User user = getCurrentBaseUser();
        admin.setId(user.getId());

        String code = admin.getCode();
        // 只修改资料，不包含修改密码，不需要发送验证码
        if (null == code) {
            userMapper.updateByPrimaryKeySelective(admin);
            return ResponseData.success();
        }

        // 获取会话验证码
        Integer status = 1;
        Map map = (Map)req.getSession().getAttribute("codeMap");
        if (null == map) {
            status = 3;
        };
        if (!code.equals(map.get("code"))) {
            status = 2;
        };
        if (1 != status) {
            return ResponseData.error().add("status", status);
        }
        req.getSession().removeAttribute("codeMap");

        // 确保只修改密码
        User u = new User();
        u.setId(user.getId());
        u.setPassword(admin.getPassword());
        userMapper.updateByPrimaryKeySelective(u);
        return ResponseData.success();
    }

    /**
     * 查询活跃用户
     * @param pageSize
     * @return
     */
    public ResponseData fSelectActiveList(Integer pageSize) {
        PageHelper.startPage(1, pageSize);
        List<User> activeUsers = userMapper.fSelectActiveList();
        return ResponseData.success().add("users", activeUsers);
    }

    /**
     * 前台根据用户id查用户详情信息
     * @param id - 用户id
     * @return
     */
    public ResponseData fSelectDetailOne(Integer id) {
        // 1.查用户信息
        User user = userMapper.fSelectOne(id);
        // 2.查用户定制的标签
        Integer uid = user.getId();
        Tag tag = new Tag();
        tag.setOid(uid);
        List<Tag> tags = tagMapper.select(tag);
        // 3.查用用关注数
        Example example1 = new Example(Concern.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("uid", uid);
        criteria1.andEqualTo("type", 3);
        Integer concernCount = concernMapper.selectCountByExample(example1);
        // 4.查被关注数
        example1.clear();
        Example.Criteria criteria2 = example1.createCriteria();
        criteria2.andEqualTo("oid", uid);
        criteria2.andEqualTo("type", 3);
        Integer fansCount = concernMapper.selectCountByExample(example1);
        // 5.查动态数,这里先只统计话题，后续加上媒体
        TopicContent topicContent = new TopicContent();
        topicContent.setUid(uid);
        Integer dynamicCount = topicContentMapper.selectCount(topicContent);
        // 6.查接收到的礼物列表，连查
        List<GiftRecord> giftRecords = giftRecordMapper.selectSendReceiveWithGift(uid);
        return ResponseData.success().add("user", user)
                .add("tags", tags)
                .add("concernCount", concernCount)
                .add("fansCount", fansCount)
                .add("dynamicCount", dynamicCount)
                .add("giftRecords", giftRecords);
    }

    /**
     * 根据用户id和动态类型查动态
     * @param id - 用户id
     * @param type - 动态类型 1-全部，2-圈子，3-问答，4-相册，5-音乐，6-视频
     * @param pageNum - 哪一页
     * @param pageSize - 分页大小
     * @return
     */
    public ResponseData fSelectDynamicList(Integer id, Byte type, Integer pageNum, Integer pageSize) {

        User user = getCurrentBaseUser();
        Integer uid = user.getId();

        List<? extends Content> contents = new ArrayList<>(10);
        PageHelper.startPage(pageNum,pageSize);
        if (1 == type) { // 查所有动态
            contents = contentService.fSelectDynamicList(id);
        } else if (2 == type || 3 == type) { // 查话题动态
            contents = topicContentService.fSelectDynamicList(id, (byte) (type-2));
        } else if (4 <= type && type <= 6) { // 查媒体动态
            contents = mediaContentService.fSelectDynamicList(id, type);
        }
        // 查看别人的动态，检查是否需要开通vip或付费
        for (Content content : contents) {
            if (id == uid) { // 自己查看自己
                content.setPrivilegeType((byte)0);
                continue;
            }
            Object[] o = getPrivilege(content);
            if ((Boolean) o[0]) { // 如果有权限
                continue;
            } else {              // 如果没有权限则置空
                content.setCover(null);
                content.setIntroduce(null);
            }
        }
        return ResponseData.success().add("contents", contents);
    }

    /**
     * 前台更新用户资料
     * @param user$ 用户
     * @return
     */
    public void fUpdateOne(User user$) {
        Integer id = getUserId();
        User user = new User();
        user.setId(id);                         // 当前用户id
        user.setQq(user$.getQq());              // qq - 可选
        user.setSay(user$.getSay());            // 个人说明 - 可选
        user.setSex(user$.getSex());            // 性别 - 可选
        user.setName(user$.getName());          // 姓名 - 可选
        user.setPhone(user$.getPhone());        // 手机号 - 可选
        user.setAvator(user$.getAvator());      // 头像url - 可选
        user.setWechat(user$.getWechat());      // 微信号 - 可选
        user.setNickname(user$.getNickname());  // 昵称 - 必填
        user.setBirthday(user$.getBirthday());  // 生日 - 可选
        userMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 前台更新用户个性化信息
     * @param user
     */
    public ResponseData fUpdatePersonalizeInfo(User user) {
        String bgm = user.getBgm();
        String bgp = user.getBgp();
        if (null == bgm && null == bgp) {
            return ResponseData.error();
        }
        User user$ = new User();
        Integer id = getUserId();
        user$.setId(id);
        user$.setBgm(bgm);
        user$.setBgp(bgp);
        userMapper.updateByPrimaryKeySelective(user$);
        return ResponseData.success();
    }
}
