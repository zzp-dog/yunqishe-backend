package com.zx.yunqishe.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.utils.JsoupUtil;
import com.zx.yunqishe.dao.TopicCommentMapper;
import com.zx.yunqishe.dao.TopicContentMapper;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.TopicComment;
import com.zx.yunqishe.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class TopicCommentService extends CommonService{

    @Autowired
    private TopicCommentMapper topicCommentMapper;

    @Autowired
    private TopicContentMapper topicContentMapper;

    /**
     * 查询话题内容回复或评论
     * @param map
     * @return
     */
    public List<TopicComment> selectTopicCommentList(Map<String, Object> map) {
        return topicCommentMapper.selectTopicCommentList(map);
    }

    /**
     * 单一插入话题内容回复或评论
     * @param topicComment
     * @return
     */
    public ResponseData insertTopicComment(TopicComment topicComment) {
        // 设置当前的用户为发表者
        Integer uid = getUserId();
        topicComment.setUid(uid);
        if(topicComment.getWid() == null) { // 被回复者id
            topicComment.setWid(-1);
        }
        if(topicComment.getPid() == null) { // 如果没有pid，这里会默认社会顶级评论
            topicComment.setPid(-1);
        }
        // xss过滤
        String text = topicComment.getText();
        String safeText = JsoupUtil.clean(text);
        topicComment.setText(safeText);
        // 插入回复
        topicCommentMapper.insertSelective(topicComment);
        return ResponseData.success();
    }

    /**
     * 根据id找话题内容回复或评论
     * @param id - 话题内容回复或评论id
     * @return
     */
    public ResponseData selectTopicCommentById(Integer id) {
        TopicComment topicComment = topicCommentMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("topicComment", topicComment);
    }

    /**
     * 根据id批量更新话题内容回复或评论
     * @param topicComments
     * @return
     */
    public ResponseData batchUpdateTopicComment(List<TopicComment> topicComments) {
        // xss过滤
        for (TopicComment topicComment : topicComments) {
            String text = topicComment.getText();
            String safeText = JsoupUtil.clean(text);
            topicComment.setText(safeText);
        }
        topicCommentMapper.batchUpdateTopicComment(topicComments);
        return ResponseData.success();
    }

    /**
     * 根据id批量或单个删除话题内容回复或评论
     * @param ids
     * @return
     */
    public ResponseData batchDeleteTopicComment(List<Integer> ids) {
        Example example = new Example(TopicComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        topicCommentMapper.deleteByExample(example);
        return ResponseData.success();
    }

    /**
     * 前台评论回复
     * @param topicComment
     * @return
     */
    public ResponseData fInsertOne(TopicComment topicComment) {
        // xss过滤
        String text = topicComment.getText();
        String safeText = JsoupUtil.clean(text);
        topicComment.setText(safeText);
        topicCommentMapper.insertSelective(topicComment);
        // 父级帖子或回复内容+1
        Integer pid = topicComment.getPid();
        Integer tcid = topicComment.getTcid();
        if (pid != -1) { // 是回复的回复，2级及以上子回复都算作1级回复的回复
            // 1级回复的回复数目+1
            topicCommentMapper.updateCommentCountAddOneById(pid);
        } else {
            // 话题内容的回复数目+1
            topicContentMapper.updateCommentCountAddOneById(tcid);
        }
        return ResponseData.success();
    }

    /**
     * 查询顶级回复（带二级回复）或只查询二级回复
     * @param map
     * @return
     */
    public ResponseData fSelectList(Map<String, Object> map) {
        // 只查询二级回复,标志：不传入顶级回复分页参数
        Integer pageNum1 = (Integer)map.get("pageNum1");
        Integer pageSize1 = (Integer) map.get("pageSize1");
        Integer pageNum2 = (Integer)map.get("pageNum2");
        Integer pageSize2 = (Integer) map.get("pageSize2");

        if (pageNum1 == null && pageSize1 == null) {
            // 使用pageHelper来查询二级回复
            PageHelper.startPage(pageNum2, pageSize2);
            map.remove("pageNum2");
            map.remove("pageSize2");
            List<TopicComment> topicComments = topicCommentMapper.fSelectLevel2List(map);
            PageInfo<TopicComment> pageInfo = new PageInfo<>(topicComments);
            return ResponseData.success().add("pageInfo", pageInfo);
        }

        // 顶级回复用pageHelper查,但其二级回复子查询用的是mysql方言查出来的
        PageHelper.startPage(pageNum1, pageSize1);
        List<TopicComment> topicComments = topicCommentMapper.fSelectLevel1List(map);
        PageInfo<TopicComment> pageInfo = new PageInfo<>(topicComments);
        return ResponseData.success().add("topicComments", pageInfo.getList());
    }
}
