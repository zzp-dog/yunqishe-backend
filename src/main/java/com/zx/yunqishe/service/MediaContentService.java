package com.zx.yunqishe.service;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.utils.JsoupUtil;
import com.zx.yunqishe.dao.MediaContentMapper;
import com.zx.yunqishe.entity.MediaContent;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.base.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class MediaContentService extends CommonService{
    @Autowired
    private MediaContentMapper mediaContentMapper;

    /**
     * 根据父pid查视频分类
     * @param pid
     * @return
     */
    public ResponseData bSelectList(Integer pid) {
        Example example = new Example(MediaContent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("pid", pid);
        example.setOrderByClause("sid asc");
        // 由于文档分类数量比较小，这里不做分页，一次查出来
        List<MediaContent> mediaContents = mediaContentMapper.selectByExample(example);
        return ResponseData.success().add("mediaContents", mediaContents);
    }

    /**
     * 后台单一插入文档节
     * @param mediaContent
     * @return
     */
    public ResponseData bInsertOne(MediaContent mediaContent) {
        // xss过滤
        String url = mediaContent.getUrl();
        String safeText = JsoupUtil.clean(url);
        mediaContent.setUrl(url);
        mediaContentMapper.insertSelective(mediaContent);
        return ResponseData.success();
    }

    /**
     * 后台单一或批量更新文档节
     * @param mediaContents
     * @return
     */
    public ResponseData bUpdateOneOrList(List<MediaContent> mediaContents) {
        for (MediaContent mediaContent : mediaContents) {
            // xss过滤
            String url = mediaContent.getUrl();
            String safeText = JsoupUtil.clean(url);
            mediaContent.setUrl(url);
            mediaContentMapper.updateByPrimaryKeySelective(mediaContent);
        }
        return ResponseData.success();
    }

    /**
     * 根据id查单个文档节
     * @param id
     * @return
     */
    public ResponseData bSelectOne(Integer id) {
        MediaContent mediaContent = mediaContentMapper.selectByPrimaryKey(id);
        return ResponseData.success().add("mediaContent", mediaContent);
    }


    /**
     * 后台条件查询文档节
     * @param searchName
     * @param searchStrategy
     * @param searchVisible
     * @return
     */
    public ResponseData bSelectFilterList(Integer pid, String searchName, Byte searchStrategy, Byte searchVisible) {
        Example example = new Example(MediaContent.class);
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
        List<MediaContent> mediaContents = mediaContentMapper.selectByExample(example);
        return ResponseData.success().add("mediaContents", mediaContents);
    }

    /**
     * 后台单个删除或批量删除文档节
     * @param ids
     * @return
     */
    public ResponseData bDeleteOneOrList(List<Integer> ids) {
        for (Integer id : ids) {
            mediaContentMapper.deleteByPrimaryKey(id);
        }
        return ResponseData.success();
    }

    /**
     * 前台根据id找媒体
     * @param id
     * @return
     */
    public ResponseData fSelectOne(Integer id) {
        MediaContent mediaContent = mediaContentMapper.selectByPrimaryKey(id);
        Object[] o = getPrivilege(mediaContent);
        if (!(boolean)o[0]) { // 没有权限查看
            mediaContent.setUrl(null);
        }
        return ResponseData.success().add("mediaContent", mediaContent);
    }

    /**
     * 根据用户id和动态类型查其媒体动态
     * @param uid - 用户id
     * @param type 4-图片，5-音乐，6-视频
     * @return
     */
    public List<MediaContent> fSelectDynamicList(Integer uid, Byte type) {
        return mediaContentMapper.fSelectDynamicList(uid, type);
    }
}
