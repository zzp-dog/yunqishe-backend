package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.EmailTemplateMapper;
import com.zx.yunqishe.entity.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class EmailTemplateService {

    @Autowired
    private EmailTemplateMapper emailTemplateMapper;

    /**
     * 根据邮件内容模板类型查模板
     * @param i 模板类型
     * @return
     */
    public EmailTemplate selectOneByType(int i) {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setType((byte)i);
        return emailTemplateMapper.selectOne(emailTemplate);
    }

    /**
     * 查询所有模板
     * @return
     */
    public List<EmailTemplate> selectAll() {
        return emailTemplateMapper.selectAll();
    }

    /**
     * 根据id查询邮件消息模板
     * @param id - 邮件消息模板id
     * @return
     */
    public EmailTemplate selectOneById(Integer id) {
        return emailTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 单个或批量删除
     * @param ids - id集合
     */
    public void batchDeleteByIds(List<Integer> ids) {
        Example example = new Example(EmailTemplate.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        emailTemplateMapper.deleteByExample(example);
    }

    /**
     * 更新单个或多个邮件消息模板
     * @param emailTemplates - 邮件消息模板集合
     */
    public void batchUpdate(List<EmailTemplate> emailTemplates) {
        for (EmailTemplate emailTemplate : emailTemplates) {
            emailTemplateMapper.updateByPrimaryKeySelective(emailTemplate);
        }
    }
}
