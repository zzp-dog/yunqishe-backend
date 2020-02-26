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
    User getUserRolePower(String account);

    /**
     * 查询简单用户信息列表,
     * status为4时在回收站显示
     * @param map 拼接的查询条件
     * @return
     */
    List<User> userSelectList(Map<String, Object> map);

    /**
     * 查询查询除隐私字段外的用户信息带角色
     *
     * @param id
     * @return
     */
    User selectOneByPrimary(Integer id);


    /**
     * 批量回收用户账号
     *
     * @param users
     */
    void batchUpdate(@Param("users") List<User> users);

    /**
     * 根据用户id找角色，
     * 在mapper里是可以根据用户id或角色id来找用户和其角色的，
     * 所以这里写两个方法进行重载，体现出他的所有职责
     * 它作为了用户关联角色的分页查询的子查询！！！
     * @param id
     * @return
     */
    User selectUserRoles(@Param("userId") Integer id);
    User selectUserRoles(@Param("userId") Integer id, @Param("roleId") Integer rid);

    /**
     * 根据id集合查用户角色集合
     * @param ids
     * @return
     */
    List<User> selectUserRolesIn(@Param("ids") List<Integer> ids);
}