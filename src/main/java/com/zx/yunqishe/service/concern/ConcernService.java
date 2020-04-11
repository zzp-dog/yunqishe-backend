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
import tk.mybatis.mapper.entity.Example;

import javax.validation.Valid;
import java.util.List;

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
        // 常规操作是：有则删除，无则插入
        // 这里用空间换时间
        concern.setConcern(concern1);
        concernMapper.fInsertOrUpdateOne(concern);
        return ResponseData.success();
    }

    /**
     * 前台用户批量关注
     * @param ids
     * @return
     */
    public ResponseData fWenyunBatchInsert(List<Integer> ids) {
        Integer uid = getUserId();
        if (uid == null) {
            return ResponseData.error(ErrorMsg.KEEP_LOGIN_ERROR);
        }
        // 先删除当前用户在问云模块关注的话题，且对应话题的关注数目-1
        Example example = new Example(Concern.class);
        Example.Criteria criteria1 = example.createCriteria();
        criteria1.andEqualTo("uid", uid);
        criteria1.andIn("oid", ids);
        concernMapper.deleteByExample(example);
        for (Integer oid : ids) {
            topicContentMapper.updateConcernAddValueById(oid, -1);
        }

        // 再插入当前用户在问云模块关注的话题，且对应话题的关注数目+1
        for (Integer oid : ids) {
            Concern concern = new Concern();
            concern.setOid(oid);
            concern.setUid(uid);
            concern.setType((byte)2);
            concernMapper.insertSelective(concern);
            topicContentMapper.updateConcernAddValueById(oid, +1);
        }
        return ResponseData.success();
    }
}
