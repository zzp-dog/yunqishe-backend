package com.zx.yunqishe.service;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.dao.MediaClassMapper;
import com.zx.yunqishe.dao.MediaContentMapper;
import com.zx.yunqishe.entity.MediaClass;
import com.zx.yunqishe.entity.MediaContent;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.entity.User;
import com.zx.yunqishe.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * 媒体分类服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MediaClassService extends CommonService{
    @Autowired
    private MediaClassMapper mediaClassMapper;

    @Autowired
    private MediaContentMapper mediaContentMapper;


    /**
     * 根据父pid查媒体分类
     * @param pid
     * @return
     */
    public ResponseData bSelectList(Integer pid) {
        Example example = new Example(MediaClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        example.setOrderByClause("sid asc");
        // 由于媒体分类数量比较小，这里不做分页，一次查出来
        List<MediaClass> mediaClasses = mediaClassMapper.selectByExample(example);
        return ResponseData.success().add("mediaClasses", mediaClasses);
    }

    /**
     * 后台单一插入媒体分类
     * @param mediaClass
     * @return
     */
    public ResponseData bInsertOne(MediaClass mediaClass) {
        Integer uid = getUserId();
        mediaClass.setUid(uid);
        mediaClassMapper.insertSelective(mediaClass);
        return ResponseData.success();
    }

    /**
     * 后台单一或批量更新媒体分类
     * @param mediaClasses
     * @return
     */
    public ResponseData bUpdateOneOrList(List<MediaClass> mediaClasses) {
        for (MediaClass media : mediaClasses) {
            mediaClassMapper.updateByPrimaryKeySelective(media);
        }
        return ResponseData.success();
    }

    /**
     * 根据id查单个媒体分类
     * @param id
     * @return
     */
    public ResponseData bSelectOne(Integer id) {
        MediaClass mediaClass = mediaClassMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("mediaClass", mediaClass);
    }


    /**
     * 后台条件查询媒体分类
     * @param map
     * @return
     */
    public ResponseData bSelectFilterList(Map<String, Object> map) {
        Byte type = (Byte)map.get("type");
        Integer pid = (Integer)map.get("pid");
        String searchName = (String)map.get("searchName");
        Byte searchStrategy = (Byte)map.get("searchStrategy");
        Byte searchVisible = (Byte)map.get("searchVisible");

        Example example = new Example(MediaClass.class);
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
        List<MediaClass> mediaClasses = mediaClassMapper.selectByExample(example);
        return ResponseData.success().add("mediaClasses", mediaClasses);
    }

    /**
     * 后台单个删除或批量删除媒体分类
     * @param ids
     * @return
     */
    public ResponseData bDeleteOneOrList(List<Integer> ids) throws UserException {
        // 先查是否被占用
        Example example = new Example(MediaClass.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("pid", ids);
        Integer count = mediaClassMapper.selectCountByExample(example);
        if (count > 0) { // 存在分类被占用，无法删除
            throw new UserException(ErrorMsg.CLASS_USED);
        }
        for (Integer id : ids) {
            mediaClassMapper.deleteByPrimaryKey(id);
        }
        return ResponseData.success();
    }

    /**
     * 前台查询前两级分类媒体
     * @return
     * @param maxSize - 一级分类的最大子分类个数
     */
    public ResponseData fSelectTop2lvList(Integer maxSize) {
        List<MediaClass> mediaClasses = mediaClassMapper.fSelectTop2lvList(maxSize);
        if (mediaClasses.size() == 0) {
            return ResponseData.error(ErrorMsg.NOT_FOUND_CONTENT);
        }
        return ResponseData.success().add("mediaClasses", mediaClasses);
    }

    /**
     * 根据id查该二级分类媒体的简介和其子媒体内容（只包含简单信息）
     * @param id - 二级分类媒体id
     * @return
     */
    public ResponseData fSelectOneWithChildren(Integer id) {
        // 二级分类媒体
        MediaClass mediaClass = mediaClassMapper.selectByPrimaryKey(id);
        // 创建者
        Integer uid = mediaClass.getUid();
        Example example0 = new Example(User.class);
        Example.Criteria criteria0 = example0.createCriteria();
        criteria0.andEqualTo("id", uid);
        example0.selectProperties(new String[]{
                "id", "nickname",
                "avator","experience"
        });
        User user = userMapper.selectOneByExample(example0);
        // 子媒体内容
        Example example = new Example(MediaContent.class);
        String[] columns = new String[]{"id","sid","price","strategy"};
        example.selectProperties(columns);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", id);

        List<MediaContent> mediaContents = mediaContentMapper.selectByExample(example);
        for (MediaContent mediaContent : mediaContents) {
            getPrivilege(mediaContent);
        }
        return ResponseData.success().add("user", user)
                .add("mediaClass", mediaClass)
                .add("mediaContents", mediaContents);
    }

    /**
     * 根据媒体分类id查媒体分类
     * @param id - 媒体分类id
     * @return
     */
    public ResponseData fSelectOne(Integer id) {
        MediaClass mediaClass = mediaClassMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("mediaClass", mediaClass);
    }
}
