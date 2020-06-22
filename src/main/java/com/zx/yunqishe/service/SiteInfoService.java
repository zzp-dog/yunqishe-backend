package com.zx.yunqishe.service;

import com.zx.yunqishe.dao.SiteInfoMapper;
import com.zx.yunqishe.entity.SiteInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站点信息查询与设置服务
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SiteInfoService {

    @Autowired
    private SiteInfoMapper siteInfoMapper;

    /**
     * 查询网站设置信息
     * @return
     */
    public SiteInfo selectSiteInfo() {
        return siteInfoMapper.selectAll().get(0);
    }

    /**
     * 后台更新站点信息
     * @param siteInfo
     */
    public void updateOne(SiteInfo siteInfo) {
        siteInfoMapper.updateByPrimaryKey(siteInfo);
    }
}
