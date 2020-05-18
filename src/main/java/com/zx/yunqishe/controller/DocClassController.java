package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.entity.DocClass;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.DocClassService;
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
@RequestMapping(API.DOC_CLASS)
public class DocClassController {

    @Autowired
    private DocClassService docClassService;

    /**
     * 根据父pid查文档分类
     * @param pid
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectList(@RequestParam(value = "pid")Integer pid) {
        return docClassService.bSelectList(pid);
    }

    /**
     * 根据id查单个文档分类
     * @param id
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectOne(@RequestParam(value = "id")Integer id) {
        return docClassService.bSelectOne(id);
    }

    /**
     * 后台条件查询文档分类
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
        map.put("type", type);
        map.put("searchName", searchName);
        map.put("searchStrategy", searchStrategy);
        map.put("searchVisible", searchVisible);
        map.put("pid", pid);
        return docClassService.bSelectFilterList(map);
    }
    /**
     * 后台单一插入文档分类
     * @param docClass
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.INSERT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bInsertOne(@RequestBody @Valid DocClass docClass) {
        return docClassService.bInsertOne(docClass);
    }

    /**
     * 后台单一或批量更新文档分类
     * @param docClasses
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.UPDATE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bUpdateOneOrList(@RequestBody @Valid List<DocClass> docClasses) {
        return docClassService.bUpdateOneOrList(docClasses);
    }

    /**
     * 后台单个删除或批量删除文档分类
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.DELETE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bDeleteOneOrList(@RequestBody @NotEmpty List<Integer> ids) throws UserException {
        return docClassService.bDeleteOneOrList(ids);
    }

    /**
     * 前台查询前两级分类文档
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+"/top2lv"+API.LIST)
    public ResponseData fSelectTop2lvList() {
        return docClassService.fSelectTop2lvList();
    }

    /**
     * 前台查询后两级：3级分类章和节
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+"/end2lv"+API.LIST)
    public ResponseData fSelectEnd2lvList(@RequestParam(name = "pid") @NotNull Integer pid) {
        return docClassService.fSelectEnd2lvList(pid);
    }
}
