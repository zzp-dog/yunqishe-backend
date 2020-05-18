package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.ContentMapper;
import com.zx.yunqishe.entity.base.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * topic_content和media_content的联合表
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ContentService {

    @Autowired
    private ContentMapper contentMapper;

    /**
     * 根据用户id查其所有动态
     * @param uid
     * @return
     */
    public List<Content> fSelectDynamicList(Integer uid) {
        return contentMapper.fSelectDynamicList(uid);
    }
}
