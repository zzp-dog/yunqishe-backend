package com.zx.yunqishe.controller.doc_content;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.entity.DocContent;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.service.doc_content.DocContentService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(API.DOC_CONTENT)
public class DocContentController {

    @Autowired
    private DocContentService docContentService;

    /**
     * 根据父pid查文档节
     * @param pid
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectList(@RequestParam(value = "pid")Integer pid) {
        return docContentService.bSelectList(pid);
    }

    /**
     * 根据id查单个文档节
     * @param id
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectOne(@RequestParam(value = "id")Integer id) {
        return docContentService.bSelectOne(id);
    }

    /**
     * 后台条件查询文档节
     * @param searchName
     * @param searchVisible
     * @param searchChange
     * @return
     */
    @GetMapping(API.BACKEND+API.SELECT+"/filter"+API.LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bSelectFilterList(
            @RequestParam(name = "pid") @NotNull Integer pid,
            @RequestParam(name = "searchName") String searchName,
            @RequestParam(name = "searchVisible") Byte searchVisible,
            @RequestParam(name = "searchCharge") Byte searchChange
    ) {
        return docContentService.bSelectFilterList(pid, searchName, searchChange, searchVisible);
    }
    /**
     * 后台单一插入文档节
     * @param docContent
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.INSERT + API.ONE)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bInsertOne(@RequestBody @Valid DocContent docContent) {
        return docContentService.bInsertOne(docContent);
    }

    /**
     * 后台单一或批量更新文档节
     * @param docContents
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.UPDATE + API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bUpdateOneOrList(@RequestBody @Valid List<DocContent> docContents) {
        return docContentService.bUpdateOneOrList(docContents);
    }

    /**
     * 后台单个删除或批量删除文档节
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.BACKEND+API.DELETE+API.ONE_OR_LIST)
    @RequiresRoles(value = {"superAdmin", "admin"})
    public ResponseData bDeleteOneOrList(@RequestBody @NotEmpty List<Integer> ids) throws UserException {
        return docContentService.bDeleteOneOrList(ids);
    }

    /**
     * 前台根据id找节
     * @param id
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.ONE)
    public ResponseData fSelectOne(@RequestParam(name = "id")Integer id) {
        return docContentService.fSelectOne(id);
    }
}
