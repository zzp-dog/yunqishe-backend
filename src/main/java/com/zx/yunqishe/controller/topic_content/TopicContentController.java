package com.zx.yunqishe.controller.topic_content;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.TopicContent;
import com.zx.yunqishe.service.topiccontent.TopicContentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(API.TOPIC_CONTENT)
public class TopicContentController {

    @Autowired
    private TopicContentService topicContentService;

    /**
     * 分页查询话筒内容列表
     * @param searchTitle
     * @param searchModule
     * @param searchCharge
     * @param searchVisible
     * @param searchCategory
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT + API.LIST)
    @RequiresPermissions("topic:select")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData selectTopicContentList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "searchTitle", required = false) String searchTitle,
            @RequestParam(value = "searchModule", required = false) Integer searchModule,
            @RequestParam(value = "searchCharge", required =  false) Integer searchCharge,
            @RequestParam(value = "searchVisible", required = false) Integer searchVisible,
            @RequestParam(value = "searchCategory", required = false) Integer searchCategory
    ) {

        Map<String, Object> map = new HashMap<>(4);
        // 所属模块
        map.put("searchModule", searchModule);
        // 是否收费
        map.put("searchCharge", searchCharge);
        // 是否可见
        map.put("searchVisible", searchVisible);
        // 所属分类
        map.put("searchCategory", searchCategory);
        // 标题关键字
        map.put("searchTitle",(searchTitle==null?null:"%"+searchTitle+"%"));

        PageHelper.startPage(pageNum, pageSize);
        List<TopicContent> topicContents = topicContentService.selectTopicContentList(map);
        PageInfo pageInfo = new PageInfo<TopicContent>(topicContents);
        return ResponseData.success().add("pageInfo", pageInfo);
    }

    /**
     * 单一插入话题内容
     * @param topicContent
     * @return
     */
    @Decrypt
    @RequiresPermissions("topic:insert")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    @PostMapping(API.INSERT)
    public ResponseData insertTopicContent(@RequestBody @Valid TopicContent topicContent) {
        return topicContentService.insertTopicContent(topicContent);
    }

    /**
     * 根据id查某个话题内容
     * @param id - 话题内容id
     */
    @GetMapping(API.SELECT + API.ONE)
    @RequiresPermissions("topic:select")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData selectTopicContent(@RequestParam(value = "id") @NotNull Integer id) {
        return topicContentService.selectTopicContentById(id);
    }

    /**
     * 根据id批量或单个更新话题内容
     * @param topicContents
     * @return
     */
    @Decrypt
    @PostMapping(API.BATCH + API.UPDATE)
    @RequiresPermissions("topic:update")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData batchUpdateTopicContent(@RequestBody @NotEmpty List<TopicContent> topicContents) {
        return topicContentService.batchUpdateTopicContent(topicContents);
    }

    /***
     * 根据id批量或单个删除话题内容
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.BATCH + API.DELETE)
    @RequiresPermissions("topic:delete")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData batchDeleteTopicContent(@RequestBody @NotEmpty List<Integer> ids) {
        return topicContentService.batchDeleteTopicContent(ids);
    }

    /**
     * 前台获取论坛帖子列表(置顶靠前)
     * @param wt 0-非问题（论坛）1-问题（问云）
     * @param tid - 所属话题，可选
     * @param type 1-全部，2-最新，3-精华，4-人气，5随机
     * @param pageNum 哪一页
     * @param pageSize list大小
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.LIST)
    public ResponseData fSelectList(
            @RequestParam(value = "wt") Byte wt,
            @RequestParam(value = "tid", required = false) Integer tid,
            @RequestParam(value = "type", defaultValue = "1") Integer type,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
            ) {
        return topicContentService.fSelectList(wt, type, pageNum, pageSize, tid);
    }

    /**
     * 前台根据帖子id查帖子详情
     * @param id
     * @return
     */
    @GetMapping(API.FRONTEND + API.SELECT + API.ONE)
    public ResponseData fSelectOne(
            @RequestParam(value = "id") Integer id) {
        return topicContentService.fSelectOne(id);
    }

    /**
     * 查询推荐内容
     * @param wt
     * @param pageSize
     * @return
     */
    @GetMapping(API.FRONTEND + API.SELECT + "/recommend" + API.LIST)
    public ResponseData fSelectRecommendList(
            @RequestParam(name = "wt") Integer wt,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return topicContentService.fSelectRecommendList(wt, pageSize);
    }
}
