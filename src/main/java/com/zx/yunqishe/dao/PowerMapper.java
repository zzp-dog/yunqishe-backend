package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.extral.res.SimplePower;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
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