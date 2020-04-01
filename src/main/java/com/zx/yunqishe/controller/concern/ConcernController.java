package com.zx.yunqishe.controller.concern;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.Concern;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.service.concern.ConcernService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
    @RequestMapping(API.FRONTEND + API.INSERT_OR_UPDATE + API.ONE)
    public ResponseData fInsertOrUpdateOne(@RequestBody @Valid Concern concern) {
        return concernService.fInsertOrUpdateOne(concern);
    }
}
