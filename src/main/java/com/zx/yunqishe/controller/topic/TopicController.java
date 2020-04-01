package com.zx.yunqishe.controller.topic;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.Topic;
import com.zx.yunqishe.service.topic.TopicService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
@RequestMapping(API.TOPIC)
public class TopicController {

    @Autowired
    private TopicService topicService;

    /**
     * 根据模块类型找话题总分类
     * @param searchModlue 0-非wt，不是问题类型；1-是问题类型；其他查询所有
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT + API.LIST)
    @RequiresPermissions("topic:select")
    public ResponseData selectTopicList(@RequestParam(value = "type" ,required = false) Integer searchModlue) {
        return topicService.selectTopicList(searchModlue);
    }

    /**
     * 批量更新或插入话题分类
     * @param topics
     * @return
     */
    @Decrypt
    @PostMapping(API.BATCH + API.INSERT_OR_UPDATE)
    @RequiresPermissions(value = {"topic:insert","topic:update"}, logical = Logical.AND)
    public ResponseData batchInsertOrUpdate(@RequestBody @Valid @NotEmpty List<Topic> topics) {
        return topicService.batchInsertOrUpdate(topics);
    }

    /**
     * 单个或批量删除
     */
    @Decrypt
    @PostMapping(API.BATCH + API.DELETE)
    @RequiresPermissions("topic:delete")
    public ResponseData batchDelete(@RequestBody @NotEmpty List<Integer> ids) {
        return topicService.batchDelete(ids);
    }

    //////////////////////////////////前台////////////////////////////////////

    /**
     * 查询话题版块，只包含可见的版块
     * @return
     */
    @GetMapping(API.FRONTEND + API.SELECT + API.LIST)
    public ResponseData fSelectList() {
        return topicService.fSelectList();
    }

    /**
     * 查询某个话题带关注信息
     * @param id
     * @return
     */
    @GetMapping(API.FRONTEND + API.SELECT + API.ONE)
    public ResponseData fSelectOne(@RequestParam Integer id) {return topicService.fSelectOne(id);}
}
