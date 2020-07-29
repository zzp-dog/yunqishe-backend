package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.BannerDisposeMapper;
import com.zx.yunqishe.entity.BannerDispose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class BannerDisposeService {

    @Autowired
    private BannerDisposeMapper bannerDisposeMapper;

    /**
     * 根据类型找图片配置列表
     * @param searchType - 类型
     * @return
     */
    public List<BannerDispose> selectListByType(Byte searchType) {
        BannerDispose BannerDispose = new BannerDispose();
        BannerDispose.setType(searchType);
        return bannerDisposeMapper.select(BannerDispose);
    }

    /**
     * 根据id找该图片配置
     * @param id - 图片配置id
     * @return
     */
    public BannerDispose selectOneById(Integer id) {
        return bannerDisposeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id集合单个或批量删除图片配置
     * @param ids
     * @return
     */
    public void batchDeleteById(List<Integer> ids) {
        Example example = new Example(BannerDispose.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        bannerDisposeMapper.deleteByExample(example);
    }

    /**
     * 单个或批量更新图片配置
     * @param bannerDisposes - 图片配置集合
     * @return
     */
    public void batchUpdateById(List<BannerDispose> bannerDisposes) {
        for (BannerDispose BannerDispose : bannerDisposes) {
            bannerDisposeMapper.updateByPrimaryKeySelective(BannerDispose);
        }
    }

    /**
     * 单个插入
     * @param BannerDispose - 图片配置
     * @return
     */
    public void insertOne(BannerDispose BannerDispose) {
        bannerDisposeMapper.insertSelective(BannerDispose);
    }
}
