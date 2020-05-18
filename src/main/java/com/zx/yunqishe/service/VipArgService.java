package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.VipArgMapper;
import com.zx.yunqishe.entity.VipArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 会员时长相关参数配置 服务层
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class VipArgService {

    @Autowired
    private VipArgMapper vipArgMapper;

    /**
     * 前台和后台根据会员类型查询列表,type不为1和2时查所有
     * @param type - 会员类型1-vip，2-svip
     * @return
     */
    public List<VipArg> uSelectList(Byte type) {
        // type不为1和2时，查所有
        if (null == type || (1 != type && 2 != type)) {
            return vipArgMapper.selectAll();
        }
        VipArg vipArg = new VipArg();
        vipArg.setType(type);
        return vipArgMapper.select(vipArg);
    }

    /**
     * 根据id删除
     * @param ids - 单个id或多个id
     * @return
     */
    public void bBatchDelete(List<Integer> ids) {
        Example example = new Example(VipArg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        vipArgMapper.deleteByExample(example);
    }

    /**
     * 根据传入的实体集合，有id则插入，无id则更新
     * @param vipArgs - 实体集合
     * @return
     */
    public void bBatchInsertOrUpdate(List<VipArg> vipArgs) {
        for (VipArg vipArg : vipArgs) {
            Integer id = vipArg.getId();
            if (null == id) {
                vipArgMapper.insertSelective(vipArg);
                continue;
            }
            vipArgMapper.updateByPrimaryKeySelective(vipArg);
        }
    }
}
