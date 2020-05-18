package com.zx.yunqishe.dao;

import com.zx.yunqishe.dao.base.UpdateThumbCommonMapper;
import com.zx.yunqishe.entity.TopicComment;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TopicCommentMapper extends Mapper<TopicComment> , UpdateThumbCommonMapper {

    /**
     * 查询话题内容评论或评论
     * @param map
     * @return
     */
    List<TopicComment> selectTopicCommentList(Map<String, Object> map);

    /**
     * 批量或单个更新话题内容回复或评论
     * @param topicComments
     */
    void batchUpdateTopicComment(@Param(value = "comments") List<TopicComment> topicComments);

    /**
     * 查询二级回复
     * @param map
     * @return
     */
    List<TopicComment> fSelectLevel2List(Map<String, Object> map);

    /**
     * 查询顶级回复带二级回复
     * @param map
     * @return
     */
    List<TopicComment> fSelectLevel1List(Map<String, Object> map);

    /**
     * 回复内容+1
     * @param id
     */
    void updateCommentCountAddOneById(Integer id);
}
