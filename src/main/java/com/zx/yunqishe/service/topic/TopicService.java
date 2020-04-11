package com.zx.yunqishe.service.topic;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.dao.TopicMapper;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.Topic;
import com.zx.yunqishe.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotEmpty;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicService extends CommonService{

    @Autowired
    private TopicMapper topicMapper;

    /**
     * 根据模块类型返回模块的总分类列表
     *
     * @param searchModlue 0-非wt，不是问题类型；1-是问题类型；其他查询所有
     * @return
     */
    public ResponseData selectTopicList(Integer searchModlue) {
        List<Topic> topics;
        if (searchModlue != null && (searchModlue == 1 || searchModlue == 0)) {
            Topic topic = new Topic();
            topic.setWt(searchModlue.byteValue());
            topics = topicMapper.select(topic);
        } else {
            topics = topicMapper.selectAll();
        }
        return ResponseData.success().add("topics", topics);
    }

    /**
     * 批量插入或更新分类
     *
     * @param topics
     * @return
     */
    public ResponseData batchInsertOrUpdate(List<Topic> topics) {
        // 分离有id和无id的
        List<Topic> noids = new ArrayList<>();
        List<Topic> hasids = new ArrayList<>();
        for (Topic topic : topics) {
            if (topic.getId() == null) {
                noids.add(topic);
                continue;
            }
            hasids.add(topic);
        }
        if (!noids.isEmpty()) {
            topicMapper.batchInsert(noids);
        }
        if (!hasids.isEmpty()) {
            topicMapper.batchUpdate(hasids);
        }
        return ResponseData.success();
    }

    /**
     * 单个或批量删除话题分类
     *
     * @param ids
     * @return
     */
    public ResponseData batchDelete(@NotEmpty List<Integer> ids) {
        Example example = new Example(Topic.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        topicMapper.deleteByExample(example);
        return ResponseData.success();
    }

    /**
     * 查询论坛版块，只包含可见的版块
     *
     * @return
     */
    public ResponseData fSelectList() {
        Topic topic = new Topic();
        topic.setWt((byte)0);
        topic.setVisible((byte)1);
        List<Topic> topics = topicMapper.select(topic);
        return ResponseData.success().add("topics", topics);
    }

    /**
     * 查询某个话题带关注信息
     * @param id
     * @return
     */
    public ResponseData fSelectOne(Integer id) {
        Integer uid = getUserId();
        Topic topic = topicMapper.fSelectOne(id, uid);
        return ResponseData.success().add("topic", topic);
    }

    /**
     * 前台问云获取已关注和未关注话题
     * 因为前台UI显示问题，所以总共最多只能查7个话题
     * @param max
     * @return
     */
    public ResponseData fWenyunSelectList(Integer max) {
        // 查已关注话题列表
        Integer uid = getUserId();
        PageHelper.startPage(1,max);
        List<Topic> topics = topicMapper.selectConcernList(1, uid);

        Map<String, Object> map = new HashMap<>();
        List<Topic> concerns = new ArrayList<>();
        List<Topic> recommends = new ArrayList<>();
        map.put("concern", concerns);
        map.put("recommend", recommends);
        for (Topic topic : topics) {
            if (null == topic.getConcernInfo() || topic.getConcernInfo().getConcern() == 0) {
                recommends.add(topic);
            } else {
                concerns.add(topic);
            }
        }
        return ResponseData.success().add("topics", map);
    }
}