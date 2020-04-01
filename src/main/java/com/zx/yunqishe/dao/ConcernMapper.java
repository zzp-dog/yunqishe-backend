package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Concern;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ConcernMapper extends Mapper<Concern> {

    /**
     * 关注或收藏
     * @param concern
     */
    void fInsertOrUpdateOne(@Param("concern") Concern concern);
}