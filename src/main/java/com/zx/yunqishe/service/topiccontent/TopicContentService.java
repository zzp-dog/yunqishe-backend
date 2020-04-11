package com.zx.yunqishe.service.topiccontent;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.dao.TopicContentMapper;
import com.zx.yunqishe.dao.TopicMapper;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.Topic;
import com.zx.yunqishe.entity.TopicContent;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.service.CommonService;
import net.sf.jsqlparser.statement.select.Top;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 话题内容服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TopicContentService extends CommonService{

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private TopicContentMapper topicContentMapper;

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
        // 则设置当前的用户为发表者,嘿嘿.
        if(topicContent.getUid() == null) {
            Integer id = getCurrentBaseUser().getId();
            topicContent.setUid(id);
        }
        // 插入话题内容
        topicContentMapper.insertSelective(topicContent);
        // 分类的内容+1
        Integer tid = topicContent.getTid();
        topicMapper.updateCountAddOneById(tid);
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
     * 获取论坛帖子列表
     * @param wt 0-非问题（论坛）1-问题（问云）
     * @param type 1-全部，2-最新，3-精华，4-人气，5-随机
     * @param pageNum 哪一页
     * @param pageSize list大小
     * @return
     */
    public ResponseData fSelectList(Byte wt, Integer type, Integer pageNum, Integer pageSize, Integer tid) {
        PageHelper.startPage(pageNum,pageSize);
        List<TopicContent> topicContents = topicContentMapper.fSelectList(wt, type, tid);
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
     * @param wt
     * @param pageSize
     * @return
     */
    public ResponseData fSelectRecommendList(Integer wt, Integer pageSize) {
        Example example = new Example(TopicContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("wt", wt);
        example.selectProperties(new String[] {
                "id", "tid", "title",
                "cover", "introduce"
        });
        PageHelper.startPage(1, pageSize);
        List<TopicContent> topicContents = topicContentMapper.selectByExample(example);
        return ResponseData.success().add("topicContents", topicContents);
    }
}
