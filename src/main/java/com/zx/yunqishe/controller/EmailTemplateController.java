package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.entity.EmailTemplate;
import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.service.EmailTemplateService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(API.EMAIL_TEMPLATE)
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    /**
     * 查询所有模板
     * @return
     */
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND+API.SELECT+API.LIST)
    public ResponseData bSelectList() {
        List<EmailTemplate> emailTemplates = emailTemplateService.selectAll();
        return ResponseData.success().add("emailTemplates", emailTemplates);
    }

    /**
     * 根据id查询邮件消息模板
     * @param id - 邮件消息模板id
     * @return
     */
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND+API.SELECT+API.ONE)
    public ResponseData bSelectOne(@RequestParam Integer id) {
        EmailTemplate emailTemplate = emailTemplateService.selectOneById(id);
        return ResponseData.success().add("emailTemplate", emailTemplate);
    }

    /**
     * 批量或单个删除
     * @param ids
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND+API.BATCH + API.DELETE)
    public ResponseData bBatchDelete(@RequestBody List<Integer> ids) {
        emailTemplateService.batchDeleteByIds(ids);
        return ResponseData.success();
    }

    /**
     * 批量或单个更新
     * @param emailTemplates - 模板集合
     * @return
     */
    @Decrypt
    @RequiresRoles(value = {"superAdmin", "admin"})
    @RequestMapping(API.BACKEND+API.BATCH + API.UPDATE)
    public ResponseData bBatchUpdate(@RequestBody List<EmailTemplate> emailTemplates) {
        emailTemplateService.batchUpdate(emailTemplates);
        return ResponseData.success();
    }
}
