package com.zx.yunqishe.service.thumb;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.dao.ThumbMapper;
import com.zx.yunqishe.dao.TopicCommentMapper;
import com.zx.yunqishe.dao.TopicContentMapper;
import com.zx.yunqishe.dao.UpdateThumbCommonMapper;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.entity.Thumb;
import com.zx.yunqishe.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(rollbackFor = Exception.class)
public class ThumbService extends CommonService{

    @Autowired
    private ThumbMapper thumbMapper;
    @Autowired
    private TopicContentMapper topicContentMapper;
    @Autowired
    private TopicCommentMapper topicCommentMapper;

    /**
     * 点赞或反对
     * @param thumb
     * @return
     */
    public ResponseData fInsertOrUpdateOne(Thumb thumb) {

        Byte v = -1;
        // 设置用户id
        thumb.setUid( getUserId());
        Byte type = thumb.getType();
        // 被点赞或反对对象id
        Integer oid = thumb.getOid();
        // value : 1-点赞，2-反对，3-无状态
        Byte value = thumb.getThumb();

        // 更新话题内容或回复的点赞和反对数
        // 点赞时value=1，点赞数+1，如果之前存在反对，则反对-1
        // 反对是value=2，反对数+1，如果之前存在点赞，则点赞-1
        // 取消反对和取消点赞时value=3，是反对-1还是点赞-1？？？查之前存在的是反对还是点赞。。
        // 综上，先查之前的点赞或反对的字段值
        UpdateThumbCommonMapper mapper = null;
        if (1 == type) { // 话题内容
            mapper = topicContentMapper;
        } else if (2 == type) { // 话题内容回复
            mapper = topicCommentMapper;
        }

        // 查之前的关注表记录，如果之前的关注值与请求的值相同
        // 则认为请求有误，直接返回重复请求
        thumb.setThumb(null);
        Thumb thumb1 = thumbMapper.selectOne(thumb);
        if (thumb1 != null) {
            v = thumb1.getThumb();
        }
        if (value == v) {
            return ResponseData.error(ErrorMsg.REQUEST_REPEAT);
        }

        if (1 == value) { // 点赞,且之前不是点赞
            mapper.updateThumbupAddValueById(oid, 1);
            if (2 == v) { // 点赞前是反对
                mapper.updateThumbdownAddValueById(oid, -1);
            }
        } else if (2 == value) { // 反对，且之前不是反对
            mapper.updateThumbdownAddValueById(oid, 1);
            if (1 == v) { // 反对前是点赞
                mapper.updateThumbupAddValueById(oid, -1);
            }
        } else if (3 == value) { // 取消反对或点赞
            if (v == 1) { // 之前是点赞
                mapper.updateThumbupAddValueById(oid, -1);
            }else if (2 == v) { // 之前是反对
                mapper.updateThumbdownAddValueById(oid, -1);
            }
        }

        // 点赞或反对添加到thumb表，有则更新，无则插入
        // 常规操作是：有则删除，无则插入
        // 这里用空间换时间
        thumb.setThumb(value);
        thumbMapper.fInsertOrUpdateOne(thumb);
        return ResponseData.success();
    }

}
