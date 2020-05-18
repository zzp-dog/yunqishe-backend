package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.base.Content;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * topic_content和media_content的联合表
 */

public interface ContentMapper {
    /**
     * 根据用户id查全部动态，联合查询
     * @param uid - 用户id
     */
    List<Content> fSelectDynamicList(Integer uid);
}
