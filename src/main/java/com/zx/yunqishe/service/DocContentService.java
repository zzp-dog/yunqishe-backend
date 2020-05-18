package com.zx.yunqishe.service;

import com.zx.yunqishe.common.utils.JsoupUtil;
import com.zx.yunqishe.dao.DocContentMapper;
import com.zx.yunqishe.entity.DocContent;
import com.zx.yunqishe.entity.core.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class DocContentService {
    @Autowired
    private DocContentMapper docContentMapper;

    /**
     * 根据父pid查文档分类
     * @param pid
     * @return
     */
    public ResponseData bSelectList(Integer pid) {
        Example example = new Example(DocContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        example.setOrderByClause("sid asc");
        // 由于文档分类数量比较小，这里不做分页，一次查出来
        List<DocContent> docContents = docContentMapper.selectByExample(example);
        return ResponseData.success().add("docContents", docContents);
    }

    /**
     * 后台单一插入文档节
     * @param docContent
     * @return
     */
    public ResponseData bInsertOne(DocContent docContent) {
        // xss过滤
        String text = docContent.getText();
        String safeText = JsoupUtil.clean(text);
        docContent.setText(text);
        docContentMapper.insertSelective(docContent);
        return ResponseData.success();
    }

    /**
     * 后台单一或批量更新文档节
     * @param docContents
     * @return
     */
    public ResponseData bUpdateOneOrList(List<DocContent> docContents) {
        for (DocContent docContent : docContents) {
            // xss过滤
            String text = docContent.getText();
            String safeText = JsoupUtil.clean(text);
            docContent.setText(text);
            docContentMapper.updateByPrimaryKeySelective(docContent);
        }
        return ResponseData.success();
    }

    /**
     * 根据id查单个文档节
     * @param id
     * @return
     */
    public ResponseData bSelectOne(Integer id) {
        DocContent docContent = docContentMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("docContent", docContent);
    }


    /**
     * 后台条件查询文档节
     * @param searchName
     * @param searchStrategy
     * @param searchVisible
     * @return
     */
    public ResponseData bSelectFilterList(Integer pid, String searchName, Byte searchStrategy, Byte searchVisible) {
        Example example = new Example(DocContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        if (null != searchStrategy){
            criteria.andEqualTo("strategy", searchStrategy);
        }
        if (null != searchVisible){
            criteria.andEqualTo("visible", searchVisible);
        }
        if (null != searchName && !"".equals(searchName)) {
            criteria.andLike("name", "%"+searchName+"%");
        }
        List<DocContent>docContents = docContentMapper.selectByExample(example);
        return ResponseData.success().add("docContents", docContents);
    }

    /**
     * 后台单个删除或批量删除文档节
     * @param ids
     * @return
     */
    public ResponseData bDeleteOneOrList(List<Integer> ids) {
        for (Integer id : ids) {
            docContentMapper.deleteByPrimaryKey(id);
        }
        return ResponseData.success();
    }

    /**
     * 前台根据id找节
     * @param id
     * @return
     */
    public ResponseData fSelectOne(Integer id) {
        DocContent docContent = docContentMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("docContent", docContent);
    }
}
