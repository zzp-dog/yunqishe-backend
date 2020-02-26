package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.entity.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
public interface UserRoleMapper extends Mapper<UserRole> {
    /**
     * 批量用户的批量角色插入
     * @param users
     */
    void batchInsert(@Param("users") List<User> users);
}