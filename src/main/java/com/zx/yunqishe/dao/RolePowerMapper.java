package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.RolePower;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;
public interface RolePowerMapper extends Mapper<RolePower> {
    /**
     * 批量插入到角色权限表
     * @param rid 角色id
     * @param powers
     */
    void batchInsert(@Param("rid") Integer rid,@Param("powers") List<Power> powers);
}