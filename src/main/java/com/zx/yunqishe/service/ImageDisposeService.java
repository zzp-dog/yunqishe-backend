package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.ImageDisposeMapper;
import com.zx.yunqishe.entity.ImageDispose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class ImageDisposeService {

    @Autowired
    private ImageDisposeMapper imageDisposeMapper;

    /**
     * 根据类型找图片配置列表
     * @param searchType - 类型
     * @return
     */
    public List<ImageDispose> selectListByType(Byte searchType) {
        ImageDispose imageDispose = new ImageDispose();
        imageDispose.setType(searchType);
        return imageDisposeMapper.select(imageDispose);
    }

    /**
     * 根据id找该图片配置
     * @param id - 图片配置id
     * @return
     */
    public ImageDispose selectOneById(Integer id) {
        return imageDisposeMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id集合单个或批量删除图片配置
     * @param ids
     * @return
     */
    public void batchDeleteById(List<Integer> ids) {
        Example example = new Example(ImageDispose.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        imageDisposeMapper.deleteByExample(example);
    }

    /**
     * 单个或批量更新图片配置
     * @param imageDisposes - 图片配置集合
     * @return
     */
    public void batchUpdateById(List<ImageDispose> imageDisposes) {
        for (ImageDispose imageDispose : imageDisposes) {
            imageDisposeMapper.updateByPrimaryKeySelective(imageDispose);
        }
    }

    /**
     * 单个插入
     * @param imageDispose - 图片配置
     * @return
     */
    public void insertOne(ImageDispose imageDispose) {
        imageDisposeMapper.insertSelective(imageDispose);
    }
}
