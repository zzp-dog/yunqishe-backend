package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.GiftRecord;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GiftRecordMapper extends Mapper<GiftRecord> {

    /**
     * 送出接收礼物表连查礼物表
     * @param uid
     * @return
     */
    List<GiftRecord> selectSendReceiveWithGift(@Param("uid") Integer uid);
}
