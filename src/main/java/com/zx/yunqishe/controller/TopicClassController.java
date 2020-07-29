package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.TopicClass;
import com.zx.yunqishe.service.TopicClassService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
@RequestMapping(API.TOPIC_CLASS)
public class TopicClassController {

    @Autowired
    private TopicClassService topicClassService;

    /**
     * 根据模块类型找话题总分类
     * @param searchModlue 0-非wt，不是问题类型；1-是问题类型；其他查询所有
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT+API.LIST)
    @RequiresPermissions("topicClass:select")
    public ResponseData selectTopicList(@RequestParam(value = "type" ,required = false) Integer searchModlue) {
        return topicClassService.selectTopicList(searchModlue);
    }

    /**
     * 批量更新或插入话题分类
     * @param topicClasses
     * @return
     */
    @Decrypt
    @PostMapping(API.BATCH+API.INSERT_OR_UPDATE)
    @RequiresPermissions(value = {"topicClass:insert","topicClass:update"}, logical = Logical.AND)
    public ResponseData batchInsertOrUpdate(@RequestBody @Valid @NotEmpty List<TopicClass> topicClasses) {
        return topicClassService.batchInsertOrUpdate(topicClasses);
    }

    /**
     * 单个或批量删除
     */
    @Decrypt
    @PostMapping(API.BATCH+API.DELETE)
    @RequiresPermissions("topicClass:delete")
    public ResponseData batchDelete(@RequestBody @NotEmpty List<Integer> ids) {
        return topicClassService.batchDelete(ids);
    }

    //////////////////////////////////前台////////////////////////////////////

    /**
     * 查询话题分类
     * @param type 0-圈子，1-问答
     * @param max type为1时默认为7(因为UI原因，后期改进)
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.LIST)
    public ResponseData fSelectList(
            @RequestParam(name="type") Integer type,
            @RequestParam(name="max", defaultValue = "7") Integer max
    ) {
        return topicClassService.fSelectList(type,max);
    }

    /**
     * 查询某个话题带关注信息
     * @param id
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.ONE)
    public ResponseData fSelectOne(@RequestParam Integer id) {return topicClassService.fSelectOne(id);}

}
