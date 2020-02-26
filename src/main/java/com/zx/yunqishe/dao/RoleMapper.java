package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Power;
import com.zx.yunqishe.entity.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RoleMapper extends Mapper<Role> {
    /**
     * 根据rloe id查角色和权限
     * @param id
     * @return
     */
    Role selectRoleWithPowers(Integer id);

    /**
     * 查询所有角色权限
     * @return
     */
    List<Power> selectRoleWithPowersAll();
}