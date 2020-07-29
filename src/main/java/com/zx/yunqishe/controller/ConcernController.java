package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.Concern;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * 批量关注话题
     * @param  type 0-圈子，1-问云
     * @param  ids 话题id集合
     * @return
     */
    @Decrypt
    @PostMapping(API.FRONTEND+API.BATCH+API.INSERT)
    public ResponseData fQABatchInsert(
            @RequestParam(name = "type") Byte type,
            @RequestBody List<Integer> ids
    ) {
        return concernService.fBatchInsert(type, ids);
    }
}
