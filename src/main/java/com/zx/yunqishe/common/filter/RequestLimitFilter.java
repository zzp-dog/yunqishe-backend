package com.zx.yunqishe.common.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 全局恶意频繁请求过滤
 */
@WebFilter
public class RequestLimitFilter implements Filter {

    /**
     * 在指定时间间隔 MAX_INTERVAL 内可请求的最大次数
     */
    private Integer MAX_COUNT = 50;
    /**
     * 时间间隔
     */
    private Integer MAX_INTERVAL = 5000;
    /**
     * 解除限制时间1小时
     */
    private Integer RELIEVE_DELAY = 1 * 3600 * 1000;
    /**
     * 主机的请求次数，上次请求时间戳，是否在限制期内等信息
     * Long[0] - 上次请求时间戳
     * Long[1] - 累计在指定时间间隔内的连续请求次数
     */
    private Map<String, Long[]> hostMap = new ConcurrentHashMap<>();
    /**
     * 解除限制任务计划
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    /**
     * 请求限制提示
     */
    private String REQUEST_LIMIT = "{\"code\": 400,\"tip\": \"已达到最大请求次数，请1小时后再访问。\"}";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String host = servletRequest.getRemoteHost();
        Long[] hostInfo = hostMap.get(host);
        Long now = System.currentTimeMillis();

        if (null == hostInfo) {
            hostMap.put(host, new Long[] {now , 1l});
        } else {
            if (hostInfo[1] > this.MAX_COUNT) { // 已到达最大请求数
                if (hostInfo[1] == this.MAX_COUNT + 1)
                    this.relieveHost(host);
                hostInfo[1]++;
                servletResponse.getWriter().println(REQUEST_LIMIT);
                hostInfo[0] = now;
                return;
            }
            if (now - hostInfo[0] < this.MAX_INTERVAL) { // 连续请求的最大时间间隔
                hostInfo[1]++;
            } else { // 重置请求数为1
                hostInfo[1] = 1l;
            }
            hostInfo[0] = now;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 清除限制
     * @param host
     */
    private void relieveHost(String host) {
        scheduledExecutorService.schedule(new Thread(()->{
            this.hostMap.remove(host);
        }), this.RELIEVE_DELAY, TimeUnit.MILLISECONDS);
    }
}
