package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Topic;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TopicMapper extends Mapper<Topic> {
    /**
     * 批量插入 topics
     * @param topics
     */
    void batchInsert(@Param("topics") List<Topic> topics);
    /**
     * 批量更新 topics
     * @param topics
     */
    void batchUpdate(@Param("topics") List<Topic> topics);

    /**
     * 分类内容加操作
     * @param tid
     */
    void updateCountAddOneById(Integer tid);

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
    Topic fSelectOne(@Param("id") Integer id, @Param("uid") Integer uid);
}