package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.response.SimplePower;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PowerMapper extends Mapper<Power> {
    /**
     * 查询简单权限列表
     * @return
     */
    @Select("select id, pid,description from power ;")
    List<SimplePower> selectSingleAll();
}