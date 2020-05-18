package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.entity.MediaClass;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.MediaClassService;
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
@RequestMapping(API.MEDIA_CLASS)
public class MediaClassController {

    @Autowired
    private MediaClassService mediaClassService;

    /**
     * 根据父pid查媒体分类
     * @param pid
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectList(@RequestParam(value = "pid")Integer pid) {
        return mediaClassService.bSelectList(pid);
    }

    /**
     * 根据id查单个媒体分类
     * @param id
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectOne(@RequestParam(value = "id")Integer id) {
        return mediaClassService.bSelectOne(id);
    }

    /**
     * 后台条件查询媒体分类
     * @param type
     * @param pid
     * @param searchName
     * @param searchStrategy
     * @param searchVisible
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+"/filter"+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectFilterList(
            @RequestParam(name = "type") @NotNull Byte type,
            @RequestParam(name = "pid") @NotNull Integer pid,
            @RequestParam(name = "searchName") String searchName,
            @RequestParam(name = "searchStrategy") Byte searchStrategy,
            @RequestParam(name = "searchVisible") Byte searchVisible
    ) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("pid", pid);
        map.put("type", type);
        map.put("searchName", searchName);
        map.put("searchStrategy", searchStrategy);
        map.put("searchVisible", searchVisible);
        return mediaClassService.bSelectFilterList(map);
    }
    /**
     * 后台单一插入媒体分类
     * @param mediaClass
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.INSERT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bInsertOne(@RequestBody @Valid MediaClass mediaClass) {
        return mediaClassService.bInsertOne(mediaClass);
    }

    /**
     * 后台单一或批量更新媒体分类
     * @param mediaClasses
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.UPDATE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bUpdateOneOrList(@RequestBody @Valid List<MediaClass> mediaClasses) {
        return mediaClassService.bUpdateOneOrList(mediaClasses);
    }

    /**
     * 后台单个删除或批量删除媒体分类
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.DELETE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bDeleteOneOrList(@RequestBody @NotEmpty List<Integer> ids) throws UserException {
        return mediaClassService.bDeleteOneOrList(ids);
    }

    /**
     * 前台查询前两级分类媒体
     * @param maxSize  - 一级分类的最大子分类个数
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+"/top2lv"+API.LIST)
    public ResponseData fSelectTop2lvList(
            @RequestParam(name="maxSize")Integer maxSize
    ) {
        return mediaClassService.fSelectTop2lvList(maxSize);
    }

    /**
     * 根据id查该二级分类媒体的简介和其子媒体内容（只包含简单信息）
     * @param id - 二级分类媒体id
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.ONE_WITH_CHILDREN)
    public ResponseData fSelectOneWithChildren(
            @RequestParam(name="id")Integer id
    ) {
        return mediaClassService.fSelectOneWithChildren(id);
    }

    /**
     * 根据媒体分类id查媒体分类
     * @param id - 媒体分类id
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.ONE)
    public ResponseData fSelectOne(
            @RequestParam(name="id")Integer id
    ) {
        return mediaClassService.fSelectOne(id);
    }

}
