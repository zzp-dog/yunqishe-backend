package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Pay;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PayMapper extends Mapper<Pay>{

    /**
     * 查询vip开通记录
     * @param uid
     * @return
     */
    List<Pay> selectPaysWithVipArgByUid(Integer uid);
}
