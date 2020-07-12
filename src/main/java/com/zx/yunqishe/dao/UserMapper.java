package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.User;
import org.apache.ibatis.annotations.Param;
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

    /**
     * 查询活跃用户
     * @return
     */
    List<User> fSelectActiveList();

    /**
     * 根据用户账号查用户基本信息
     * @param account
     * @return
     */
    User selectBaseOneByAccount(String account);

    /**
     * 前台根据用户id查用户id
     * @param id
     * @return
     */
    User fSelectOne(Integer id);

    /**
     * 首页查询用户列表（只包含昵称，性别，头像和id）
     * @param sex - -1-全部，1-男，2-女，3-未知
     * @param type - -1-全部，1- 活跃，2-新人，3-随机
     * @return
     */
    List<User> fSelectList(@Param("sex") Byte sex, @Param("type") Byte type);
}