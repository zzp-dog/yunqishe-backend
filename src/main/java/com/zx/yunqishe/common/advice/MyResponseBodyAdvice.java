package com.zx.yunqishe.common.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.utils.EncryptUtil;
import com.zx.yunqishe.entity.core.ResponseData;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 加密
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.zx.yunqishe")

public class MyResponseBodyAdvice implements ResponseBodyAdvice {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @SneakyThrows
    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse){
        Boolean needEncrypt = methodParameter.getMethod().isAnnotationPresent(Encrypt.class);
        if(needEncrypt) {
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            String sk = (String)session.getAttribute("sk");
            if (null == sk) {
                return  ResponseData.error(ErrorMsg.SK_EXPIRE_ERROR);
            }
            String mes = objectMapper.writeValueAsString(o);
            try {
                // 这里直接返回字符串，因为最终返回给前端的都是json串
                o = EncryptUtil.AESEncrypt(mes, sk);
            } catch (Exception e) {
                return ResponseData.error(ErrorMsg.ENCRYPT_ERROR);
            }
        }
        return o;
    }
}
