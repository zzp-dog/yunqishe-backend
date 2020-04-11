package com.zx.yunqishe.controller.topic_comment;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.TopicComment;
import com.zx.yunqishe.service.topiccomment.TopicCommentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(API.TOPIC_COMMENT)
public class TopicCommentController {
    @Autowired
    private TopicCommentService topicCommentService;

    /**
     * 分页查询话筒内容列表
     * @param searchText
     * @param searchCharge
     * @param searchVisible
     * @return
     */
    @Encrypt
    @GetMapping(API.SELECT + API.LIST)
    @RequiresPermissions("topic:select")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData selectTopicCommentList(
            @RequestParam(value = "tcid", required = true) @NotNull Integer tcid,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value = "searchCharge", required =  false) Integer searchCharge,
            @RequestParam(value = "searchVisible", required = false) Integer searchVisible
    ) {

        Map<String, Object> map = new HashMap<>(4);
        // 所属话题内容回复的id
        map.put("tcid", tcid);
        // 是否收费
        map.put("searchCharge", searchCharge);
        // 是否可见
        map.put("searchVisible", searchVisible);
        // 标题关键字
        map.put("searchText",(searchText==null?null:"%"+searchText+"%"));

        PageHelper.startPage(pageNum, pageSize);
        List<TopicComment> topicComments = topicCommentService.selectTopicCommentList(map);
        PageInfo pageInfo = new PageInfo<TopicComment>(topicComments);
        return ResponseData.success().add("pageInfo", pageInfo);
    }

    /**
     * 单一插入话题内容回复
     * @param topicComment
     * @return
     */
    @Decrypt
    @RequiresPermissions("topic:insert")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    @PostMapping(API.INSERT)
    public ResponseData insertTopicComment(@RequestBody @Valid TopicComment topicComment) {
        return topicCommentService.insertTopicComment(topicComment);
    }

    /**
     * 根据id查某个话题内容回复
     * @param id - 话题内容回复id
     */
    @GetMapping(API.SELECT + API.ONE)
    @RequiresPermissions("topic:select")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData selectTopicComment(@RequestParam(value = "id") @NotNull Integer id) {
        return topicCommentService.selectTopicCommentById(id);
    }

    /**
     * 根据id批量或单个更新话题内容回复
     * @param topicComments
     * @return
     */
    @Decrypt
    @PostMapping(API.BATCH + API.UPDATE)
    @RequiresPermissions("topic:update")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData batchUpdateTopicComment(@RequestBody @NotEmpty List<TopicComment> topicComments) {
        return topicCommentService.batchUpdateTopicComment(topicComments);
    }

    /***
     * 根据id批量或单个删除话题内容回复
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.BATCH + API.DELETE)
    @RequiresPermissions("topic:delete")
    @RequiresRoles(value = {"superAdmin", "admin"}, logical = Logical.OR)
    public ResponseData batchDeleteTopicComment(@RequestBody @NotEmpty List<Integer> ids) {
        return topicCommentService.batchDeleteTopicComment(ids);
    }

    /////////////////////////////////////FRONTEND///////////////////////////////////

    /**
     * 前台评论回复
     * @return
     */
    @Decrypt
    @PostMapping(API.FRONTEND+API.INSERT+API.ONE)
    public ResponseData fInsertOne(@RequestBody @Valid TopicComment topicComment) {
        return topicCommentService.fInsertOne(topicComment);
    }

    /**
     * 查询顶级回复（带二级回复）或只查询二级回复
     * @param pid 父评论id -1表示顶级评论
     * @param pageNum2 二级回复的页
     * @param pageSize2 二级回复的页大小
     * @param tcid 所属话题内容回复的id
     * @param pageNum1 顶级回复的页
     * @param pageSize1 顶级回复的页大小
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.LIST)
    public ResponseData fSelectList(
            @RequestParam(value = "pid") Integer pid,
            @RequestParam(value = "tcid", required = false)Integer tcid,
            @RequestParam(value = "pageNum2") Integer pageNum2,
            @RequestParam(value = "pageNum1", required = false) Integer pageNum1,
            @RequestParam(value = "pageSize2") @Max(value = 50) Integer pageSize2,
            @RequestParam(value = "pageSize1", required = false) @Max(value = 50) Integer pageSize1
    ) {
        // 参数太多打包一下，封装成map传过去。。。
        Map<String, Object> map = new HashMap<>(8);
        map.put("pid", pid);
        map.put("tcid", tcid);
        map.put("pageNum1", pageNum1);
        map.put("pageNum2", pageNum2);
        map.put("pageSize1", pageSize1);
        map.put("pageSize2", pageSize2);
        return topicCommentService.fSelectList(map);
    }
}
