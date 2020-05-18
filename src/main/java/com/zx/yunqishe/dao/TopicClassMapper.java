package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.TopicClass;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TopicClassMapper extends Mapper<TopicClass> {
    /**
     * 批量插入 topicClasses
     * @param topicClasses
     */
    void batchInsert(@Param("topicClasses") List<TopicClass> topicClasses);
    /**
     * 批量更新 topicClasses
     * @param topicClasses
     */
    void batchUpdate(@Param("topicClasses") List<TopicClass> topicClasses);

    /**
     * 分类内容加操作
     * @param pid
     */
    void updateCountAddOneById(Integer pid);

    /**
     * 关注或取消关注
     * @param oid
     * @param value
     */
    void updateConcernAddValueById(@Param("id")Integer oid, @Param("value") Integer value);

    /**
     * 查询某个话题带关注信息
     * @param id
     * @param uid
     * @return
     */
    TopicClass fSelectOne(@Param("id") Integer id, @Param("uid") Integer uid);

    /**
     * 查询用户关注话题列表
     * @param type 所属模块 0-圈子，1-问云
     * @param uid
     * @return
     */
    List<TopicClass> selectConcernList(@Param("type") int type, @Param("uid") Integer uid);
}