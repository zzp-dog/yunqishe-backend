package com.zx.yunqishe.dao;

import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.annotation.RegisterMapper;

@RegisterMapper
public interface UpdateThumbCommonMapper {
    /**
     * 更新点赞数
     * @param oid
     * @param value
     */
    void updateThumbupAddValueById(@Param("id") Integer oid, @Param("value")Integer value);

    /**
     * 更新反对数
     * @param oid
     * @param value
     */
    void updateThumbdownAddValueById(@Param("id") Integer oid, @Param("value")Integer value);
}
