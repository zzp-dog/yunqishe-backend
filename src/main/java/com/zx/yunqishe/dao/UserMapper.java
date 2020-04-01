package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper extends Mapper<User> {

    /**
     * 根据用户账号查用户，角色和权限
     *
     * @param account
     * @return
     */
    User selectUserWithRolesWithPowers(String account);

    /**
     * 查询用户信息列表,
     * status为5时在回收站显示
     * @param map 拼接的查询条件
     * @return
     */
    List<User> selectUsersWithRolesByConditions(Map<String, Object> map);

    /**
     * 查询查询除隐私字段外的用户信息带角色
     * @param id
     * @return
     */
    User selectUserWithRolesByUserId(Integer id);


    /**
     * 批量回收用户账号
     *
     * @param users
     */
    void batchUpdate(@Param("users") List<User> users);

    /**
     * 根据用户id集合查用户角色集合
     * @param ids
     * @return
     */
    List<User> selectUserRolesUserIdIn(@Param("ids") List<Integer> ids);

    /**
     * 关注或取消关注
     * @param oid
     * @param value
     */
    void updateConcernAddValueById(@Param("id") Integer oid, @Param("value") Integer value);
}