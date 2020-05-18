package com.zx.yunqishe.common.advice;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.common.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 解密，解密后变成json字符串，然后转成对象
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.zx.yunqishe")
public class MyRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    /**
     * 在@RequestBody 前执行
     * @param httpInputMessage
     * @param methodParameter
     * @param type
     * @param aClass
     * @return
     * @throws IOException
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException{
        // 获取方法
        Method method = methodParameter.getMethod();
        // 是否需要解密
        boolean decrypt = method.isAnnotationPresent(Decrypt.class);
        if(!decrypt) return httpInputMessage;
        // 需要解密
        try {
            return new MyHttpInputMessage(httpInputMessage);
        } catch (Exception e) {
            // 只能抛IOException， 解密异常抛不出去！！！
            // 没有sk
            if (e instanceof UserException) {
                throw new IOException(ErrorMsg.SK_EXPIRE_ERROR);
            }
            // 密钥超时，解密错误
            throw new IOException(ErrorMsg.REQUEST_DECRYPT_ERROR);
        }
    }

    /**
     * 在@RequestBody后执行
     * @param o
     * @param httpInputMessage
     * @param methodParameter
     * @param type
     * @param aClass
     * @return
     */
    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    /**
     * 定义自已的请求头和请求体中间件
     */
    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders httpHeaders;
        private InputStream body;
        public MyHttpInputMessage() {}
        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            String mes = inputStream2Str(inputMessage.getBody());
            // 需要解密
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            String sk = (String) session.getAttribute("sk");
            if (null == sk) {
                throw new UserException(ErrorMsg.SK_EXPIRE_ERROR);
            }
            mes = EncryptUtil.AESDecrypt(mes, sk);
            this.httpHeaders = inputMessage.getHeaders();
            this.body = new ByteArrayInputStream(mes.getBytes());
        }
        @Override
        public InputStream getBody(){
            return this.body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.httpHeaders;
        }

        /**
         * 请求体流变字符串
         * @param inputStream
         * @return
         * @throws Exception
         */
        private String inputStream2Str(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));
            String str = null;
            StringBuilder sb = new StringBuilder();
            while ((str = bufferedReader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        }
    }
}
