package com.zx.yunqishe.service;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.zx.yunqishe.dao.ChargeMapper;
import com.zx.yunqishe.entity.Charge;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional(rollbackFor = Exception.class)
public class ChargeService extends CommonService{

    @Autowired
    private ChargeMapper chargeMapper;

    /**
     * 查询是否存在付费记录
     * @param oid - 需要付费内容的id
     * @param type - 需要付费内容的类型
     * @return
     */
    public Integer fSelectCount(Integer oid, Byte type) {
        Integer uid = getUserId();
        Charge charge = new Charge();
        charge.setOid(oid);
        charge.setUid1(uid);
        charge.setType(type);
        return chargeMapper.selectCount(charge);
    }

}
