package com.zx.yunqishe.common.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.annotation.Encrypt;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.common.utils.EncryptUtil;
import com.zx.yunqishe.entity.ResponseData;
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
import java.io.IOException;
import java.lang.reflect.Method;

@RestControllerAdvice(basePackages = "com.zx.yunqishe")

public class EncryptResponseBodyAdvice implements ResponseBodyAdvice {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Boolean needEncrypt = methodParameter.getMethod().isAnnotationPresent(Encrypt.class);
        if(needEncrypt) {
            // 获取session
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            // 获取私钥
            String sk = (String)session.getAttribute("sk");
            if (null == sk) {
                return  ResponseData.error(ErrorMsg.SK_EXPIRE_ERROR);
            }
            try {
                String mes = objectMapper.writeValueAsString(o);
                o = EncryptUtil.AESEncrypt(mes, sk);
            } catch (Exception e) {
                return ResponseData.error(ErrorMsg.ENCRYPT_ERROR);
            }
        }
        return o;
    }
}
