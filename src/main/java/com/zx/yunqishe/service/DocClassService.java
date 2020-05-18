package com.zx.yunqishe.service;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.dao.DocClassMapper;
import com.zx.yunqishe.entity.DocClass;
import com.zx.yunqishe.entity.core.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 文档分类服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DocClassService {
    @Autowired
    private DocClassMapper docClassMapper;


    /**
     * 根据父pid查文档分类
     * @param pid
     * @return
     */
    public ResponseData bSelectList(Integer pid) {
        Example example = new Example(DocClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        example.setOrderByClause("sid asc");
        // 由于文档分类数量比较小，这里不做分页，一次查出来
        List<DocClass> docClasses = docClassMapper.selectByExample(example);
        return ResponseData.success().add("docClasses", docClasses);
    }

    /**
     * 后台单一插入文档分类
     * @param docClass
     * @return
     */
    public ResponseData bInsertOne(DocClass docClass) {
        docClassMapper.insertSelective(docClass);
        return ResponseData.success();
    }

    /**
     * 后台单一或批量更新文档分类
     * @param docClasses
     * @return
     */
    public ResponseData bUpdateOneOrList(List<DocClass> docClasses) {
        for (DocClass docClass : docClasses) {
            docClassMapper.updateByPrimaryKeySelective(docClass);
        }
        return ResponseData.success();
    }

    /**
     * 根据id查单个文档分类
     * @param id
     * @return
     */
    public ResponseData bSelectOne(Integer id) {
        DocClass docClass = docClassMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("docClass", docClass);
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
        Byte searchStrategy = (Byte)map.get("searchStrategy");
        Byte searchVisible = (Byte)map.get("searchVisible");

        Example example = new Example(DocClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        criteria.andEqualTo("type", type);
        if (null != searchStrategy){
            criteria.andEqualTo("strategy", searchStrategy);
        }
        if (null != searchVisible){
            criteria.andEqualTo("visible", searchVisible);
        }
        if (null != searchName && !"".equals(searchName)) {
            criteria.andLike("name", "%"+searchName+"%");
        }
        List<DocClass> docClasses = docClassMapper.selectByExample(example);
        return ResponseData.success().add("docClasses", docClasses);
    }

    /**
     * 后台单个删除或批量删除文档分类
     * @param ids
     * @return
     */
    public ResponseData bDeleteOneOrList(List<Integer> ids) throws UserException {
        // 先查是否被占用
        Example example = new Example(DocClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("pid", ids);
        Integer count = docClassMapper.selectCountByExample(example);
        if (count > 0) { // 存在分类被占用，无法删除
            throw new UserException(ErrorMsg.CLASS_USED);
        }
        for (Integer id : ids) {
            docClassMapper.deleteByPrimaryKey(id);
        }
        return ResponseData.success();
    }

    /**
     * 前台查询前两级分类文档
     * @return
     */
    public ResponseData fSelectTop2lvList() {
        List<DocClass> docClasses = docClassMapper.fSelectTop2lvList();
        if (docClasses.size() == 0) {
            return ResponseData.error(ErrorMsg.NOT_FOUND_CONTENT);
        }
        return ResponseData.success().add("docClasses", docClasses);
    }

    /**
     * 前台查询后两级：3级分类章和节
     * @return
     */
    public ResponseData fSelectEnd2lvList(Integer pid) {
        List<DocClass> docClasses = docClassMapper.fSelectEnd2lvList(pid);
        if (docClasses.size() == 0) {
            return ResponseData.error(ErrorMsg.NOT_FOUND_CONTENT);
        }
        return ResponseData.success().add("docClasses", docClasses);
    }
}
