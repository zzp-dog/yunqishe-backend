package com.zx.yunqishe.controller.doc_class;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.entity.Doc;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.service.doc_class.DocService;
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
@RequestMapping(API.DOC)
public class DocController {

    @Autowired
    private DocService docService;

    /**
     * 根据父pid查文档分类
     * @param pid
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectList(@RequestParam(value = "pid")Integer pid) {
        return docService.bSelectList(pid);
    }

    /**
     * 根据id查单个文档分类
     * @param id
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectOne(@RequestParam(value = "id")Integer id) {
        return docService.bSelectOne(id);
    }

    /**
     * 后台条件查询文档分类
     * @param type
     * @param pid
     * @param searchName
     * @param searchChange
     * @param searchVisible
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+"/filter"+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectFilterList(
            @RequestParam(name = "type") @NotNull Byte type,
            @RequestParam(name = "pid") @NotNull Integer pid,
            @RequestParam(name = "searchName") String searchName,
            @RequestParam(name = "searchCharge") Byte searchChange,
            @RequestParam(name = "searchVisible") Byte searchVisible
    ) {
        Map<String, Object> map = new HashMap<>(8);
        map.put("pid", pid);
        map.put("type", type);
        map.put("searchName", searchName);
        map.put("searchChange", searchChange);
        map.put("searchVisible", searchVisible);
        return docService.bSelectFilterList(map);
    }
    /**
     * 后台单一插入文档分类
     * @param doc
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.INSERT + API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bInsertOne(@RequestBody @Valid Doc doc) {
        return docService.bInsertOne(doc);
    }

    /**
     * 后台单一或批量更新文档分类
     * @param docs
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.UPDATE + API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bUpdateOneOrList(@RequestBody @Valid List<Doc> docs) {
        return docService.bUpdateOneOrList(docs);
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
        return docService.bDeleteOneOrList(ids);
    }

    /**
     * 前台查询前两级分类文档
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+"/top2lv"+API.LIST)
    public ResponseData fSelectTop2lvList() {
        return docService.fSelectTop2lvList();
    }

    /**
     * 前台查询后两级：3级分类章和节（继承于文档分类）
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+"/end2lv"+API.LIST)
    public ResponseData fSelectEnd2lvList(@RequestParam(name = "pid") @NotNull Integer pid) {
        return docService.fSelectEnd2lvList(pid);
    }
}
