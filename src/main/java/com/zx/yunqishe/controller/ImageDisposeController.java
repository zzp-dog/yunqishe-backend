package com.zx.yunqishe.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.ImageDispose;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.ImageDisposeService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(API.IMAGE_DISPOSE)
public class ImageDisposeController {
    @Autowired
    private ImageDisposeService imageDisposeService;

    /**
     * 后台分页查询图片配置列表
     *
     * @param searchType 所属模块类型1-首页，2-圈子，3-微视频，默认1
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND + API.SELECT + API.LIST)
    public ResponseData bSelectList(
            @RequestParam Byte searchType,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        PageHelper.startPage(pageNum, pageSize);
        List<ImageDispose> imageDisposes = imageDisposeService.selectListByType(searchType);
        PageInfo<ImageDispose> pageInfo = new PageInfo<>(imageDisposes);
        return ResponseData.success().add("pageInfo", pageInfo);
    }

    /**
     * 根据id找该图片配置
     *
     * @param id - 图片配置id
     * @return
     */
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND + API.SELECT + API.ONE)
    public ResponseData bSelectOne(
            @RequestParam Integer id
    ) {
        ImageDispose imageDispose = imageDisposeService.selectOneById(id);
        return ResponseData.success().add("imageDispose", imageDispose);
    }

    /**
     * 根据id集合单个或批量删除图片配置
     *
     * @param ids
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND + API.BATCH + API.DELETE)
    public ResponseData bBatchDelete(
            @RequestBody List<Integer> ids
    ) {
        imageDisposeService.batchDeleteById(ids);
        return ResponseData.success();
    }

    /**
     * 单个或批量更新图片配置
     *
     * @param imageDisposes - 图片配置集合
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND + API.BATCH + API.UPDATE)
    public ResponseData bBatchUpdate(@RequestBody List<ImageDispose> imageDisposes) {
        imageDisposeService.batchUpdateById(imageDisposes);
        return ResponseData.success();
    }

    /**
     * 单个插入
     *
     * @param imageDispose - 图片配置
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND + API.INSERT + API.ONE)
    public ResponseData bInsertOne(@RequestBody ImageDispose imageDispose) {
        imageDisposeService.insertOne(imageDispose);
        return ResponseData.success();
    }

    /////////////////////////////////////////////fronted/////////////////////////////

    /**
     * 前台根据配置类型查配置链接
     * @param type 1-首页,2-圈子,3-知行，4-微媒体
     * @return
     */
    @RequestMapping(API.FRONTEND + API.SELECT + API.LIST)
    public ResponseData fSelectList(@RequestParam Byte type) {
        List<ImageDispose> imageDisposes = imageDisposeService.selectListByType(type);
        return ResponseData.success().add("imageDisposes", imageDisposes);
    }
}
