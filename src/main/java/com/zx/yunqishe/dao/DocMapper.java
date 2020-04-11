package com.zx.yunqishe.dao;

import com.zx.yunqishe.entity.Doc;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DocMapper extends Mapper<Doc> {
    /** 前台查询前两级分类文档 */
    List<Doc> fSelectTop2lvList();

    /** 前台查询后两级：3级分类章和节（继承于文档分类） */
    List<Doc> fSelectEnd2lvList(Integer pid);
}