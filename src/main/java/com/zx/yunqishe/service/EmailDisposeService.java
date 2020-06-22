package com.zx.yunqishe.service;

import com.zx.yunqishe.common.cache.AppCache;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.dao.EmailDisposeMapper;
import com.zx.yunqishe.entity.EmailDispose;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmailDisposeService {
    /** 邮箱设置dao */
    @Autowired
    private EmailDisposeMapper emailDisposeMapper;

    /** 查询激活的邮箱设置 */
    public EmailDispose selectActiveSendEmail() {
        EmailDispose emailDispose = new EmailDispose();
        emailDispose.setActive((byte)1);
        return emailDisposeMapper.selectOne(emailDispose);
    }

    /**
     * 查询所有邮件设置
     * @return
     */
    public List<EmailDispose> bSelectList() {
       return  emailDisposeMapper.selectAll();
    }

    /**
     * 后台批量或单个删除,至少要保留一个
     * @param ids
     */
    public void bBatchDelete(List<Integer> ids) throws Exception {
        Integer count = emailDisposeMapper.selectCount(null);
        if (count == ids.size()) {
            throw new UserException(ErrorMsg.RETAIN_ONE);
        }
        Example example = new Example(EmailDispose.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        emailDisposeMapper.deleteByExample(example);
    }

    /**
     * 根据传入的实体集合，有id则插入，无id则更新
     * @param emailDisposes - 实体集合
     * @return
     */
    public void bBatchInsertOrUpdate(List<EmailDispose> emailDisposes) {
        for (EmailDispose emailDispose : emailDisposes) {
            Integer id = emailDispose.getId();
            if (null == id) {
                emailDisposeMapper.insertSelective(emailDispose);
                continue;
            }
            emailDisposeMapper.updateByPrimaryKeySelective(emailDispose);
        }
        // 改变全局邮件设置缓存
        setAppEmailDispose(emailDisposes);
    }

    /**
     * 设置全局的邮件设置
     * @param emailDisposes
     */
    private void setAppEmailDispose(List<EmailDispose> emailDisposes) {
        EmailDispose emailDispose = AppCache.emailDispose;
        for (EmailDispose dispose : emailDisposes) {
            if (1 == dispose.getActive()) {
                AppCache.emailDispose = dispose;
                return;
            }
        }
    }
}
