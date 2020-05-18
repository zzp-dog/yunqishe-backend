package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.Concern;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping(API.CONCERN)
public class ConcernController {

    @Autowired
    private ConcernService concernService;

    /**
     * 关注或收藏
     * @param concern
     * @return
     */
    @Decrypt
    @RequestMapping(API.FRONTEND+API.INSERT_OR_UPDATE+API.ONE)
    public ResponseData fInsertOrUpdateOne(@RequestBody @Valid Concern concern) {
        return concernService.fInsertOrUpdateOne(concern);
    }

    /**
     * 前台用户批量关注
     * @param ids
     * @return
     */
    @Decrypt
    @PostMapping(API.FRONTEND+"/qa"+API.BATCH+API.INSERT)
    public ResponseData fQABatchInsert(@RequestBody List<Integer> ids) {
        return concernService.fQABatchInsert(ids);
    }
}
