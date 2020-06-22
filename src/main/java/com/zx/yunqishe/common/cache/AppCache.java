package com.zx.yunqishe.common.cache;

import com.zx.yunqishe.entity.EmailDispose;
import com.zx.yunqishe.entity.Switch;
import com.zx.yunqishe.service.EmailDisposeService;
import com.zx.yunqishe.service.SwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("appCache")
public class AppCache {

    /** 全局缓存的开关信息 */
    public static Switch mySwitch;
    /** 全局缓存的激活的邮箱设置 */
    public static EmailDispose emailDispose;

    @Autowired
    private SwitchService switchService;
    @Autowired
    private EmailDisposeService emailDisposeService;

    /**
     * 系统启动
     */
    @PostConstruct
    private void init(){
        selectSwitch();
        selectActiveSendEmail();
    }

    public void selectSwitch() {
        // 查询开关操作设置
        mySwitch = switchService.selectSwitch();
    }

    public void selectActiveSendEmail() {
        // 查询激活的邮箱设置
        emailDispose = emailDisposeService.selectActiveSendEmail();
    }

    /**
     * 系统运行结束
     */
    @PreDestroy
    public void destroy(){
    }
}