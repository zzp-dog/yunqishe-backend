package com.zx.yunqishe.service;

import com.github.pagehelper.PageHelper;
import com.zx.yunqishe.dao.TopicClassMapper;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.TopicClass;
import com.zx.yunqishe.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicClassService extends CommonService{

    @Autowired
    private TopicClassMapper topicClassMapper;

    /**
     * 根据模块类型返回模块的总分类列表
     *
     * @param searchModlue 0-非type，不是问题类型；1-是问题类型；其他查询所有
     * @return
     */
    public ResponseData selectTopicList(Integer searchModlue) {
        List<TopicClass> topicClasses;
        if (searchModlue != null && (searchModlue == 1 || searchModlue == 0)) {
            TopicClass topicClass = new TopicClass();
            topicClass.setType(searchModlue.byteValue());
            topicClasses = topicClassMapper.select(topicClass);
        } else {
            topicClasses = topicClassMapper.selectAll();
        }
        return ResponseData.success().add("topicClasses", topicClasses);
    }

    /**
     * 批量插入或更新分类
     *
     * @param topicClasses
     * @return
     */
    public ResponseData batchInsertOrUpdate(List<TopicClass> topicClasses) {
        // 分离有id和无id的
        List<TopicClass> noids = new ArrayList<>();
        List<TopicClass> hasids = new ArrayList<>();
        for (TopicClass topicClass : topicClasses) {
            if (topicClass.getId() == null) {
                noids.add(topicClass);
                continue;
            }
            hasids.add(topicClass);
        }
        if (!noids.isEmpty()) {
            topicClassMapper.batchInsert(noids);
        }
        if (!hasids.isEmpty()) {
            topicClassMapper.batchUpdate(hasids);
        }
        return ResponseData.success();
    }

    /**
     * 单个或批量删除话题分类
     *
     * @param ids
     * @return
     */
    public ResponseData batchDelete(List<Integer> ids) {
        Example example = new Example(TopicClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        topicClassMapper.deleteByExample(example);
        return ResponseData.success();
    }

    /**
     * 查询话题分类
     * @return
     * @param type
     * @param maxSize
     */
    public ResponseData fSelectList(Integer type, Integer maxSize) {
        if (0==type) {
            return this.fCircleSelectList();
        } else {
            return this.fQASelectList(maxSize);
        }
    }

    /**
     * 查询某个话题带关注信息
     * @param id
     * @return
     */
    public ResponseData fSelectOne(Integer id) {
        Integer uid = getUserId();
        TopicClass topicClass = topicClassMapper.fSelectOne(id, uid);
        return ResponseData.success().add("topicClass", topicClass);
    }

    /**
     * 查询圈子所有分类
     * @return
     */
    private ResponseData fCircleSelectList() {
        TopicClass topicClass = new TopicClass();
        topicClass.setType((byte)0);
        topicClass.setVisible((byte)1);
        List<TopicClass> topicClasses = topicClassMapper.select(topicClass);
        return ResponseData.success().add("topicClasses", topicClasses);
    }

    /**
     * 前台问云获取已关注和未关注话题
     * 因为前台UI显示问题，所以总共最多只能查7个话题
     * @param max
     * @return
     */
    private ResponseData fQASelectList(Integer max) {
        // 查已关注话题列表
        Integer uid = getUserId();
        PageHelper.startPage(1,max);
        List<TopicClass> topicClasses = topicClassMapper.selectConcernList(1, uid);

        Map<String, Object> map = new HashMap<>();
        List<TopicClass> concerns = new ArrayList<>();
        List<TopicClass> recommends = new ArrayList<>();
            map.put("concern", concerns); // 已关注
        map.put("recommend", recommends); // 未关注
        for (TopicClass topicClass : topicClasses) {
            if (null == topicClass.getConcernInfo() || topicClass.getConcernInfo().getConcern() == 0) {
                recommends.add(topicClass);
            } else {
                concerns.add(topicClass);
            }
        }
        return ResponseData.success().add("topicClasses", map);
    }
}