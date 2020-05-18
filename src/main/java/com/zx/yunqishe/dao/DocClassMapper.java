package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.DocClass;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DocClassMapper extends Mapper<DocClass> {
    /** 前台查询前两级分类文档 */
    List<DocClass> fSelectTop2lvList();

    /** 前台查询后两级：3级分类章和节 */
    List<DocClass> fSelectEnd2lvList(Integer pid);
}