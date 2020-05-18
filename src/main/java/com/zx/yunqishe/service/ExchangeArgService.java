package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.ExchangeArgMapper;
import com.zx.yunqishe.entity.ExchangeArg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
/**
 * 云币兑换服务层
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExchangeArgService {

    @Autowired
    private ExchangeArgMapper exchangeArgMapper;

    /**
     * 前台和后台查询列表
     * @return
     */
    public List<ExchangeArg> uSelectList() {
        return exchangeArgMapper.selectAll();
    }

    /**
     * 根据id删除
     * @param ids - 单个id或多个id
     * @return
     */
    public void bBatchDelete(List<Integer> ids) {
        Example example = new Example(ExchangeArg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        exchangeArgMapper.deleteByExample(example);
    }

    /**
     * 根据传入的实体集合，有id则插入，无id则更新
     * @param exchangeArgs - 实体集合
     * @return
     */
    public void bBatchInsertOrUpdate(List<ExchangeArg> exchangeArgs) {
        for (ExchangeArg exchangeArg : exchangeArgs) {
            Integer id = exchangeArg.getId();
            if (null == id) { // 插入
                exchangeArgMapper.insertSelective(exchangeArg);
                continue;
            }
            // 更新
            exchangeArgMapper.updateByPrimaryKeySelective(exchangeArg);
        }
    }
}
