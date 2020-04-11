package com.zx.yunqishe.service.doc_class;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.dao.DocMapper;
import com.zx.yunqishe.entity.Doc;
import com.zx.yunqishe.entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 文档分类服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocService {
    @Autowired
    private DocMapper docMapper;


    /**
     * 根据父pid查文档分类
     * @param pid
     * @return
     */
    public ResponseData bSelectList(Integer pid) {
        Example example = new Example(Doc.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        example.setOrderByClause("sid asc");
        // 由于文档分类数量比较小，这里不做分页，一次查出来
        List<Doc> docs = docMapper.selectByExample(example);
        return ResponseData.success().add("docs", docs);
    }

    /**
     * 后台单一插入文档分类
     * @param doc
     * @return
     */
    public ResponseData bInsertOne(Doc doc) {
        docMapper.insertSelective(doc);
        return ResponseData.success();
    }

    /**
     * 后台单一或批量更新文档分类
     * @param docs
     * @return
     */
    public ResponseData bUpdateOneOrList(List<Doc> docs) {
        for (Doc doc : docs) {
            docMapper.updateByPrimaryKeySelective(doc);
        }
        return ResponseData.success();
    }

    /**
     * 根据id查单个文档分类
     * @param id
     * @return
     */
    public ResponseData bSelectOne(Integer id) {
        Doc doc = docMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("doc", doc);
    }


    /**
     * 后台条件查询文档分类
     * @param map
     * @return
     */
    public ResponseData bSelectFilterList(Map<String, Object> map) {
        Byte type = (Byte)map.get("type");
        Integer pid = (Integer)map.get("pid");
        String searchName = (String)map.get("searchName");
        Byte searchChange = (Byte)map.get("searchChange");
        Byte searchVisible = (Byte)map.get("searchVisible");

        Example example = new Example(Doc.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        criteria.andEqualTo("type", type);
        if (null != searchChange){
            criteria.andEqualTo("charge", searchChange);
        }
        if (null != searchVisible){
            criteria.andEqualTo("visible", searchVisible);
        }
        if (null != searchName && !"".equals(searchName)) {
            criteria.andLike("name", "%"+searchName+"%");
        }
        List<Doc>docs = docMapper.selectByExample(example);
        return ResponseData.success().add("docs", docs);
    }

    /**
     * 后台单个删除或批量删除文档分类
     * @param ids
     * @return
     */
    public ResponseData bDeleteOneOrList(List<Integer> ids) throws UserException {
        // 先查是否被占用
        Example example = new Example(Doc.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("pid", ids);
        Integer count = docMapper.selectCountByExample(example);
        if (count > 0) { // 存在分类被占用，无法删除
            throw new UserException(ErrorMsg.CLASS_USED);
        }
        for (Integer id : ids) {
            docMapper.deleteByPrimaryKey(id);
        }
        return ResponseData.success();
    }

    /**
     * 前台查询前两级分类文档
     * @return
     */
    public ResponseData fSelectTop2lvList() {
        List<Doc> docs = docMapper.fSelectTop2lvList();
        return ResponseData.success().add("docs", docs);
    }

    /**
     * 前台查询后两级：3级分类章和节（继承于文档分类）
     * @return
     */
    public ResponseData fSelectEnd2lvList(Integer pid) {
        List<Doc> docs = docMapper.fSelectEnd2lvList(pid);
        return ResponseData.success().add("docs", docs);
    }
}
