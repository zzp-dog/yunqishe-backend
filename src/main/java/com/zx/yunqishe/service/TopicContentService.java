package com.zx.yunqishe.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.utils.JsoupUtil;
import com.zx.yunqishe.dao.ConcernMapper;
import com.zx.yunqishe.dao.TopicContentMapper;
import com.zx.yunqishe.dao.TopicClassMapper;
import com.zx.yunqishe.entity.Concern;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.TopicContent;
import com.zx.yunqishe.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 话题内容服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TopicContentService extends CommonService{

    @Autowired
    private TopicClassMapper topicClassMapper;

    @Autowired
    private TopicContentMapper topicContentMapper;

    @Autowired
    private ConcernMapper concernMapper;

    /**
     * 查询话题内容
     * @param map
     * @return
     */
    public List<TopicContent> selectTopicContentList(Map<String, Object> map) {
        return topicContentMapper.selectTopicContentList(map);
    }

    /**
     * 单一插入话题内容
     * @param topicContent
     * @return
     */
    public ResponseData insertTopicContent(TopicContent topicContent) {
        // 如果当前的话题内容无发表者的id，
        // 则设置当前的用户为发表者
        Integer id = getCurrentBaseUser().getId();
        topicContent.setUid(id);
        // xss过滤
        String text = topicContent.getText();
        String safeText = JsoupUtil.clean(text);
        topicContent.setText(safeText);
        // 插入话题内容
        topicContentMapper.insertSelective(topicContent);
        // 分类的内容+1
        Integer id2 = topicContent.getPid();
        topicClassMapper.updateCountAddOneById(id2);
        // 返回操作成功
        return ResponseData.success();
    }

    /**
     * 根据id找话题内容
     * @param id - 话题内容id
     * @return
     */
    public ResponseData selectTopicContentById(Integer id) {
        TopicContent topicContent = topicContentMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("topicContent", topicContent);
    }

    /**
     * 根据id批量更新话题内容
     * @param topicContents
     * @return
     */
    public ResponseData batchUpdateTopicContent(List<TopicContent> topicContents) {
        // xss过滤
        for (TopicContent topicContent : topicContents) {
            String text = topicContent.getText();
            String safeText = JsoupUtil.clean(text);
            topicContent.setText(safeText);
        }
        topicContentMapper.batchUpdateTopicContent(topicContents);
        return ResponseData.success();
    }

    /**
     * 根据id批量或单个删除话题内容
     * @param ids
     * @return
     */
    public ResponseData batchDeleteTopicContent(List<Integer> ids) {
        Example example = new Example(TopicContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        topicContentMapper.deleteByExample(example);
        return ResponseData.success();
    }

    /**
     * 获取话题内容列表
     * @param type1 0-非问题（圈子）1-问题（问云）
     * @param type2 1-全部，2-最新，3-精华，4-人气，5-随机
     * @param isFree
     *@param pageNum 哪一页
     * @param pageSize list大小   @return
     */
    public ResponseData fSelectList(Byte type1, Integer type2, Boolean isFree, Integer pageNum, Integer pageSize, Integer pid) {
        PageHelper.startPage(pageNum,pageSize);
        List<TopicContent> topicContents = topicContentMapper.fSelectList(type1, type2, pid,isFree);
        PageInfo<TopicContent> pageInfo = new PageInfo<>(topicContents);
        return ResponseData.success().add("topicContents", pageInfo.getList());
    }

    /**
     * 前台根据帖子id查帖子详情
     * @param id
     * @return
     */
    public ResponseData fSelectOne(Integer id) {
        // 获取当前用户id
        Integer uid = getUserId();
        TopicContent topicContent = topicContentMapper.fSelectOne(id, uid);
        // 浏览次数加1
        topicContentMapper.updateViewAddValueById(id, 1);
        return ResponseData.success().add("topicContent", topicContent);
    }

    /**
     * 查询推荐内容
     * @param type 0-圈子，1-问云
     * @return
     */
    public List<TopicContent> fSelectRecommendList(Integer type) {
        Example example = new Example(TopicContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", type);
        example.selectProperties(new String[] {"id", "pid", "title", "cover", "introduce"});
        Integer uid = getUserId();
        // 未登录，查所有
        if (null == uid) {
            return topicContentMapper.selectByExample(example);
        }
        // 已登录，查关注
        Example example1 = new Example(Concern.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("uid", uid);
        criteria1.andEqualTo("type", type+2);
        example1.selectProperties(new String[]{"oid"});
        List<Concern> concerns = concernMapper.selectByExample(example1);
        List<Integer> ids = new ArrayList<>(concerns.size());
        for (Concern concern : concerns) {
            ids.add(concern.getOid());
        }
        if (ids.size() > 0) {
            criteria.andIn("id", ids);
        }
        return topicContentMapper.selectByExample(example);
    }

    /**
     * 根据用户id和动态类型查其话题动态
     * @param uid - 用户id
     * @param type 0-圈子，1-问答
     * @return
     */
    public List<TopicContent> fSelectDynamicList(Integer uid, Byte type) {
        return topicContentMapper.fSelectDynamicList(uid, type);
    }
}
