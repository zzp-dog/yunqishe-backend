package com.zx.yunqishe.trunk.other.aop;

import com.zx.yunqishe.trunk.other.annotation.Decrypt;
import com.zx.yunqishe.trunk.entity.ResponseData;
import com.zx.yunqishe.trunk.other.exception.UserException;
import com.zx.yunqishe.trunk.other.utils.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

@ControllerAdvice(basePackages = "com.zx.yunqishe")
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {
    private static final Logger logger = LoggerFactory.getLogger(RequestBodyAdvice.class);
    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException{
        // 获取方法
        Method method = methodParameter.getMethod();
        // 是否需要解密
        if (!method.isAnnotationPresent(Decrypt.class)) {
            return httpInputMessage;
        }
        try {
            return new MyHttpInputMessage(httpInputMessage);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("对方法["+method.getName()+"]请求参数解密异常");
            return httpInputMessage;
        }
    }
    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Nullable
    @Override
    public Object handleEmptyBody(@Nullable Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders httpHeaders;
        private InputStream body;
        public MyHttpInputMessage(HttpInputMessage inputMessage) throws Exception {
            // 获取session
            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session = request.getSession();
            String sk = (String)session.getAttribute("sk");
            if (null == sk) {
                throw new UserException(ResponseData.error().add("msg", "获取密钥失败"));
            }
            this.httpHeaders = inputMessage.getHeaders();
            String mes = inputStream2Str(inputMessage.getBody());
            this.body = new ByteArrayInputStream(
                    EncryptUtil.AESDecrypt(mes, sk).getBytes());
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
//            byte[] bytes = new byte[inputStream.available()];
//            inputStream.read(bytes);
//            return new String(bytes, "UTF-8");
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
