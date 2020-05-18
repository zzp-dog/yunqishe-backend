package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.MediaClass;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface MediaClassMapper extends Mapper<MediaClass> {
    /** 前台查询前两级分类
     * @param maxSize - 一级分类的最大子分类个数*/
    List<MediaClass> fSelectTop2lvList(@Param("maxSize") Integer maxSize);

    /** 前台查询后两级：二级媒体分类和媒体内容 */
    List<MediaClass> fSelectEnd2lvList(Integer pid);
}