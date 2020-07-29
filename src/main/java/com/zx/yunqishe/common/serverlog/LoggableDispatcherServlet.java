package com.zx.yunqishe.common.serverlog;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传时，不能打印日志，会清空请求！！！
 */
@Component
@Profile("dev")
public class LoggableDispatcherServlet extends DispatcherServlet {
    private static final Logger logger = LoggerFactory.getLogger("HttpLogger");
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        //创建一个 json 对象，用来存放 http 日志信息
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.put("uri", requestWrapper.getRequestURI());
        rootNode.put("clientIp", requestWrapper.getRemoteAddr());
        rootNode.set("requestHeaders", mapper.valueToTree(getRequestHeaders(requestWrapper)));
        String method = requestWrapper.getMethod();
        rootNode.put("method", method);
        try {
            super.doDispatch(requestWrapper, responseWrapper);
        } finally {
            if(method.equals("GET")) {
                rootNode.set("request", mapper.valueToTree(requestWrapper.getParameterMap()));
            } else {
                JsonNode newNode = null;
                try{
                    newNode = mapper.readTree(requestWrapper.getContentAsByteArray());
                } catch (Exception e) {
                    // 异常时将接收的内容变成json字符串，打印看看是啥
                    // 备注：非json格式字符串(如本项目前端加密后数据未转换为json字符串)会报错，首尾加上双引号
                    if (e instanceof JsonParseException) {
                        String str = "\""+new String(requestWrapper.getContentAsByteArray(), "UTF-8")+"\"";
                        newNode =   mapper.readTree(str.getBytes());
                    } else {
                        e.printStackTrace();
                    }
                }
                rootNode.set("request", newNode);
            }

            rootNode.put("status", responseWrapper.getStatus());
            JsonNode newNode = mapper.readTree(responseWrapper.getContentAsByteArray());
            rootNode.set("response", newNode);

            responseWrapper.copyBodyToResponse();

            rootNode.set("responseHeaders", mapper.valueToTree(getResponseHeaders(responseWrapper)));
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
            logger.info(json);
        }
    }

    private Map<String, Object> getRequestHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;

    }

    private Map<String, Object> getResponseHeaders(ContentCachingResponseWrapper response) {
        Map<String, Object> headers = new HashMap<>();
        Collection<String> headerNames = response.getHeaderNames();
        for (String headerName : headerNames) {
            headers.put(headerName, response.getHeader(headerName));
        }
        return headers;
    }

}
