package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.MediaContent;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MediaContentMapper extends Mapper<MediaContent> {

    /**
     * 根据用户id和动态类型查其媒体动态
     * @param uid
     * @param type
     * @return
     */
    List<MediaContent> fSelectDynamicList(@Param("uid") Integer uid,@Param("type") Byte type);
}