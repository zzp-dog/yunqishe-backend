package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(API.LEVEL)
public class LevelController {

    @Autowired
    private LevelService levelService;

    /**
     * 前台查询所有用户等级
     * @return
     */
    @GetMapping(API.FRONTEND+API.SELECT+API.LIST)
    public ResponseData fSelectList() {
        return levelService.fSelectList();
    }
}
