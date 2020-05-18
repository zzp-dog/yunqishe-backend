package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.Thumb;
import com.zx.yunqishe.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(API.THUMB)
public class ThumbController {

    @Autowired
    private ThumbService thumbService;

    /**
     * 点赞或反对
     * @param thumb
     * @return
     */
    @Decrypt
    @RequestMapping(API.FRONTEND+API.INSERT_OR_UPDATE+API.ONE)
    public ResponseData fInsertOrUpdate(@RequestBody @Valid Thumb thumb) {
        return thumbService.fInsertOrUpdateOne(thumb);
    }
}
