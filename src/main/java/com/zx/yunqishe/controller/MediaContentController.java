package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.entity.MediaContent;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.MediaContentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(API.MEDIA_CONTENT)
public class MediaContentController {

    @Autowired
    private MediaContentService mediaContentService;

    /**
     * 根据父pid查媒体
     * @param pid
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectList(@RequestParam(value = "pid")Integer pid) {
        return mediaContentService.bSelectList(pid);
    }

    /**
     * 根据id查单个媒体
     * @param id
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectOne(@RequestParam(value = "id")Integer id) {
        return mediaContentService.bSelectOne(id);
    }

    /**
     * 后台条件查询媒体
     * @param searchName
     * @param searchVisible
     * @param searchStrategy
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+"/filter"+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectFilterList(
            @RequestParam(name = "pid") @NotNull Integer pid,
            @RequestParam(name = "searchName") String searchName,
            @RequestParam(name = "searchVisible") Byte searchVisible,
            @RequestParam(name = "searchStrategy") Byte searchStrategy
    ) {
        return mediaContentService.bSelectFilterList(pid, searchName, searchStrategy, searchVisible);
    }
    /**
     * 后台单一插入媒体
     * @param mediaContent
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.INSERT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bInsertOne(@RequestBody @Valid MediaContent mediaContent) {
        return mediaContentService.bInsertOne(mediaContent);
    }

    /**
     * 后台单一或批量更新媒体
     * @param mediaContents
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.UPDATE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bUpdateOneOrList(@RequestBody @Valid List<MediaContent> mediaContents) {
        return mediaContentService.bUpdateOneOrList(mediaContents);
    }

    /**
     * 后台单个删除或批量删除媒体内容
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.DELETE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bDeleteOneOrList(@RequestBody @NotEmpty List<Integer> ids) throws UserException {
        return mediaContentService.bDeleteOneOrList(ids);
    }

    //////////////////////////////////前台////////////////////////////////

    /**
     * 前台根据id找媒体内容
     * @param id
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.ONE)
    public ResponseData fSelectOne(@RequestParam(name = "id")Integer id) {
        return mediaContentService.fSelectOne(id);
    }
}
