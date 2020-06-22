package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.SwitchMapper;
import com.zx.yunqishe.entity.Switch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class SwitchService {

    @Autowired
    private SwitchMapper switchMapper;

    /**
     * 查询开关
     * @return
     */
    public Switch selectSwitch() {
        return switchMapper.selectAll().get(0);
    }

    /**
     * 更新开关
     * @param mySwitch
     */
    public void updateOne(Switch mySwitch) {
        switchMapper.updateByPrimaryKey(mySwitch);
    }
}
