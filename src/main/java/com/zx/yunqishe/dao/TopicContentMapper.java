package com.zx.yunqishe.dao;

import com.zx.yunqishe.dao.base.UpdateThumbCommonMapper;
import com.zx.yunqishe.entity.TopicContent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TopicContentMapper extends Mapper<TopicContent> , UpdateThumbCommonMapper {
    /**
     * 查询话题内容列表
     * @param map
     * @return
     */
    List<TopicContent> selectTopicContentList(Map<String, Object> map);

    /**
     * 根据id批量更新话题内容
     * @param topicContents
     */
    void batchUpdateTopicContent(@Param(value = "contents") List<TopicContent> topicContents);

    /**
     * 获取话题内容列表
     * @param type1 0-非问题（圈子）1-问题（问云）
     * @param type2 1-置顶，2-最新，3-精华，4-人气，5-随机，其他或不传为全部
     * @param isFree
     * @return
     */
    List<TopicContent> fSelectList(@Param("type1") Byte type1, @Param("type2") Integer type2, @Param("pid") Integer pid, @Param("isFree") Boolean isFree);

    /**
     * 前台根据帖子id查帖子详情
     * @param id
     * @return
     */
    TopicContent fSelectOne(@Param("id") Integer id, @Param("uid") Integer uid);

    /**
     * 回复内容+1
     * @param id
     */
    void updateCommentCountAddOneById(Integer id);

    /**
     * 取消或关注话题内容
     * @param oid
     * @param value
     */
    void updateConcernAddValueById(@Param("id") Integer oid, @Param("value") Integer value);

    /**
     * 更新浏览次数
     * @param id
     * @param i
     */
    void updateViewAddValueById(@Param("id") Integer id, @Param("value") int i);

    /**
     * 根据用户id和动态类型查其话题动态
     * @param uid
     * @param type
     * @return
     */
    List<TopicContent> fSelectDynamicList(@Param("uid") Integer uid, @Param("type") Byte type);
}