package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.LevelMapper;
import com.zx.yunqishe.entity.Level;
import com.zx.yunqishe.entity.core.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LevelService {
    @Autowired
    private LevelMapper levelMapper;

    /**
     * 前台查询所有用户等级
     * @return
     */
    public ResponseData fSelectList() {
        Example example = new Example(Level.class);
        example.setOrderByClause("experience asc");
        List<Level> levels = levelMapper.selectByExample(example);
        return ResponseData.success().add("levels", levels);
    }
}
