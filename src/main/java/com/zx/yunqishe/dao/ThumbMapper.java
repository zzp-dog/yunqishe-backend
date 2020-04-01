package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Thumb;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface ThumbMapper extends Mapper<Thumb> {
    /**
     * 点赞或反对
     * @param thumb
     */
    void fInsertOrUpdateOne(@Param("thumb") Thumb thumb);
}