package com.zx.yunqishe.service.concern;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.dao.ConcernMapper;
import com.zx.yunqishe.dao.TopicContentMapper;
import com.zx.yunqishe.dao.TopicMapper;
import com.zx.yunqishe.dao.UserMapper;
import com.zx.yunqishe.entity.Concern;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional(rollbackFor = Exception.class)
public class ConcernService extends CommonService{

    @Autowired
    private ConcernMapper concernMapper;

    @Autowired
    private TopicContentMapper topicContentMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 关注或收藏
     * @param concern
     * @return
     */
    public ResponseData fInsertOrUpdateOne(Concern concern) {

        concern.setUid(getUserId());
        Byte type = concern.getType();
        Integer oid = concern.getOid();
        Byte concern1 = concern.getConcern();
        Integer value = concern1 == 1 ? 1 : -1;

        // 查之前的关注表记录，如果之前的关注值与请求的值相同
        // 则认为请求有误，直接返回重复请求
        Byte v = null;
        concern.setConcern(null);
        Concern previous = concernMapper.selectOne(concern);
        if (previous != null) {
            v = previous.getConcern();
        }
        if (concern1 == v) {
            return ResponseData.error(ErrorMsg.REQUEST_REPEAT);
        }
        // 对应的内容计数也要加一或减一
        if (type == 1) {     // 关注话题内容
            topicContentMapper.updateConcernAddValueById(oid, value);
        } else if (type == 2) { // 关注话题
            topicMapper.updateConcernAddValueById(oid, value);
        } else if (type == 3) { // 关注用户
            userMapper.updateConcernAddValueById(oid, value);
        }

        // 有则更新无则插入，这里针对mysql只发一条sql完成操作
        // 常规操作是两步完成，1-先查，2-后插入或更新
        concern.setConcern(concern1);
        concernMapper.fInsertOrUpdateOne(concern);
        return ResponseData.success();
    }
}
