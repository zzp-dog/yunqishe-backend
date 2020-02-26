package com.zx.yunqishe.service.power;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.dao.PowerMapper;
import com.zx.yunqishe.dao.RolePowerMapper;
import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.RolePower;
import com.zx.yunqishe.entity.extral.res.SimplePower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class PowerService {
    @Autowired
    private  PowerMapper powerMapper;
    @Autowired
    private RolePowerMapper rolePowerMapper;
    /**
     * 查询简单的权限列表
     * @return
     */
    public List<SimplePower> selectSimpleAll() {
        return powerMapper.selectSingleAll();
    }
    /** 查询所有权限 */
    public List<Power> selectAll() {
        return powerMapper.selectAll();
    }

    /**
     * 根据权限id查其所有信息
     * @param id
     * @return
     */
    public Power powerSelect(Integer id) {
        Power power  = new Power();
        power.setId(id);
        return powerMapper.selectOne(power);
    }

    /**
     * 插入权限
     * @param power
     * @return
     */
    public ResponseData powerInsert(Power power) {
        Integer pid = power.getPid();
        // 新增的权限节点为顶级权限时
        if (pid == null)power.setPid(-1);
        powerMapper.insertSelective(power);
        return ResponseData.success();
    }

    /**
     * 根据权限id更新实体
     * @param power 权限实体
     * @return
     */
    public ResponseData powerUpdate(Power power) {
        Integer id = power.getId();
        Integer pid = power.getPid();
        // 检查pid是否为自身id或子孙id
        if(!validPid(id, pid)) {
            return ResponseData.error(ErrorMsg.POWER_PID_ERROR);
        }
        powerMapper.updateByPrimaryKeySelective(power);
        return ResponseData.success();
    }

    /**
     * 单个删除和批量删除
     * @param ids 权限id数组
     * @return
     */
    public ResponseData powerDelete(List<Integer> ids) {
        // 1.检查是否被角色占用
        Example example = new Example(RolePower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("powerId", ids);
        Integer count =rolePowerMapper.selectCountByExample(example);
        if (count > 0) {
            return ResponseData.error(ErrorMsg.POWER_BEUSED);
        }
        // 2.检查是否被自身占用
        Example example1 = new Example(Power.class);
        criteria = example1.createCriteria();
        criteria.andIn("pid", ids);
        Integer count1 = powerMapper.selectCountByExample(example1);
        if(count1 > 0) {
            return ResponseData.error(ErrorMsg.POWER_BEUSEDBYCHILD);
        }
        // 3.批量删除
        example1.clear();
        criteria = example1.createCriteria();
        criteria.andIn("id", ids);
        powerMapper.deleteByExample(example1);
        return ResponseData.success();
    }

    /**
     * 检查是否设置了子权限为父权限
     * @param id 自身id
     * @param pid 父id
     * @return true - pid不为子孙或自身id  false-pid为子孙或自身id
     */
    private boolean validPid(Integer id, Integer pid) {
        // 0.查询所有权限
        List<Power> powers = powerMapper.selectAll();
        if(powers==null)return false;
        Map<Integer, Power> map = new HashMap<>(powers.size());
        // 1.构建map，散列查询比较快
        for (Power power : powers) {
            map.put(power.getId(),power);
        }
        // 2.构建权限树或森林
        for (Power power : powers) {
            Integer p = power.getPid();
            // 顶级权限
            if(p==-1)continue;
            Power parent = map.get(p);
            if(parent==null)continue;
            if(parent.getPowers() == null) {
                List<Power> list = new ArrayList<>();
                parent.setPowers(list);
            }
            parent.getPowers().add(power);
        }
        // 3.找子孙节点
        Power self = map.get(id);
        map.clear();
        map.put(id,self);
        Map<Integer,Power> map1 = getChildsAndSelf(self,map);
        // pid是否为子孙或自身id
        return map1.get(pid) == null;
    }

    private Map<Integer,Power> getChildsAndSelf(Power self, Map<Integer, Power> map) {
        List<Power> powers = self.getPowers();
        if(powers!=null && !powers.isEmpty()) {
            for (Power power : powers) {
                map.put(power.getId(),power);
                getChildsAndSelf(power, map);
            }
        }
        return map;
    }
}
