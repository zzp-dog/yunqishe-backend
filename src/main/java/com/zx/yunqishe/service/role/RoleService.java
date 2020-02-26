package com.zx.yunqishe.service.role;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.dao.PowerMapper;
import com.zx.yunqishe.dao.RoleMapper;
import com.zx.yunqishe.dao.RolePowerMapper;
import com.zx.yunqishe.dao.UserRoleMapper;
import com.zx.yunqishe.entity.*;
import com.zx.yunqishe.entity.extral.res.SimplePower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePowerMapper rolePowerMapper;
    @Autowired
    private PowerMapper powerMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 查询所有角色
     * @return
     */
    public List<Role> selectAll() {
        return roleMapper.selectAll();
    }

    public Role selectRoleWithPowers(Integer id) {
        return  roleMapper.selectRoleWithPowers(id);
    }

    /**
     * 添加角色，控制器已用注解校验父id必须存在！！！
     * @param role
     */
    public ResponseData insertRole(Role role) {
        // 插入角色并返回id
        roleMapper.insertSelective(role);
        Integer id = role.getId();
        // 设置了权限
        List<Power> powers = role.getPowers();
        if(powers !=null && powers.isEmpty()){
            // 检查权限是否父权限的子权限
            if(!validPid(role.getPid(), powers)) {
                return ResponseData.error(ErrorMsg.ROLE_POWER_BOUND);
            }
            rolePowerMapper.batchInsert(id, powers);
        }
        // 批量插入到角色权限表
        return ResponseData.success();
    }

    /**
     * 检查为角色设置的权限是否越界，即权限不能超过父角色
     * @param pid 父角色id
     * @param powers 要设置的角色
     * @return
     */
    private boolean validPid(Integer pid, List<Power> powers) {
       // 没有设置权限,返回false
        if(powers == null) return true;
        // 查询父角色及其权限集合
        Role parent = roleMapper.selectRoleWithPowers(pid);
        // 获取父角色权限[不包含子孙权限]
        List<Power> ppowers = parent.getPowers();
        // 父角色没有设置权限，但是自己却设置了权限，返回false
        if(ppowers == null || ppowers.isEmpty()){
            return false;
        }
        // 查询所有权限
        List<Power> allpowers = powerMapper.selectAll();
        if(allpowers == null) return false;
        Map<Integer,Power> allmap = new HashMap<>();
        for (Power power$ : allpowers) {
            allmap.put(power$.getId(),power$);
        }
        // 构建树或森林
        for (Power $power : allpowers) {
            Integer id = $power.getPid();
            if (id == -1) continue;
            Power p = allmap.get(id);
            if(p==null)continue;
            List<Power> powerList = p.getPowers();
            if (powerList == null) {
                powerList = new ArrayList<>();
                p.setPowers(powerList);
            }
            powerList.add($power);
        }
        // 找出父角色所有权限[包含子孙权限和权限本身]存贮至pmap
        Map<Integer,Power> pmap = new HashMap<>();
        // 遍历父权限找自身和子孙节点
        setMap(pmap,allmap,ppowers);
        // 判断父节点所有权限是否包含要设置的权限
        boolean flag = true;
        for (Power power : powers) {
            Integer key = power.getId();
            flag &=pmap.get(key) != null;
            if(!flag)return false;
        }
        return flag;
    }

    /**
     * 将找到的节点都放置再resultMap中
     * @param resultMap
     * @param allMap
     * @param src
     */
    private final  void setMap(Map<Integer,Power> resultMap, Map<Integer, Power> allMap, List<Power> src) {
        for (Power power : src) {
            Integer id = power.getId();
            resultMap.put(id, power);
            Power p = allMap.get(id);
            if (p == null)continue;
            getChilds(p, resultMap);
        };
    }

    /**
     * 获取子节点
     * @param p
     * @param pmap
     */
    private final void getChilds(Power p, Map<Integer, Power> pmap) {
        List<Power> powers = p.getPowers();
        if(powers ==null || powers.isEmpty()){
            return;
        }
        for (Power power : powers) {
            pmap.put(power.getId(),power);
            getChilds(power, pmap);
        }
    }

    /**
     * 根据角色id更新角色
     * @param role
     */
    public ResponseData updateRole(Role role) {
        // 检查权限是否父权限的子权限
        if(!validPid(role.getPid(), role.getPowers())) {
            return ResponseData.error(ErrorMsg.ROLE_POWER_BOUND);
        }
        // 更新角色表
        roleMapper.updateByPrimaryKeySelective(role);
        Integer rid = role.getId();
        // 删除权限角色
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",rid);
        rolePowerMapper.deleteByExample(example);
        // 有设置权限
        // 插入权限角色表
        List<Power> powers = role.getPowers();
        if(powers!=null && !powers.isEmpty()){
            rolePowerMapper.batchInsert(rid, powers);
        }
        return ResponseData.success();
    }

    /**
     * 单个和批量删除
     * @param ids
     */
    public ResponseData batchDelete(List<Integer> ids) {
        // 先检查角色是否被占用
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("roleId", ids);
        Integer count = userRoleMapper.selectCountByExample(example);
        if (count > 0) return ResponseData.error(ErrorMsg.ROLE_BEUSED);
        // 检查是否存在本表占用情况
        Example example1 = new Example(Role.class);
        criteria = example1.createCriteria();
        criteria.andIn("pid", ids);
        Integer count1 = roleMapper.selectCountByExample(example1);
        if (count1 > 0) return ResponseData.error(ErrorMsg.ROLE_BEUSEDBYCHILD);
        // 删除角色权限表中的相关记录
        Example example2 = new Example(RolePower.class);
        criteria= example2.createCriteria();
        criteria.andIn("roleId", ids);
        rolePowerMapper.deleteByExample(example2);
        // 删除角色
        example1.clear();
        criteria = example1.createCriteria();
        criteria.andIn("id", ids);
        roleMapper.deleteByExample(example1);
        return ResponseData.success();
    }

    /**
     * 查询角色列表
     * @return
     */
    public ResponseData roleSelectList() {
        List<Role> roles = roleMapper.selectAll();
        return ResponseData.success().add("roles", roles);
    }
}
