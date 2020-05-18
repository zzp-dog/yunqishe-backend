package com.zx.yunqishe.common.advice;

import com.zx.yunqishe.entity.core.ResponseData;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 全局错误处理器
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.zx.yunqishe")
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseData handleException(Exception e) {
        // 打印日志
        log.error("全局异常处理", e);
        // 请求参数校验错误信息，有以下三种异常
        BindingResult bindResult;
        Map<String, Object> map = null;
        if (e instanceof BindException) { // 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
            BindException ex = (BindException) e;
            bindResult = ex.getBindingResult();
            map = getBindResultMap(bindResult);
        } else if (e instanceof MethodArgumentNotValidException) { // spring抛出的，处理请求参数或实体格式错误 validate失败后抛出的异常
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            bindResult = ex.getBindingResult();
            map = getBindResultMap(bindResult);
        } else if (e instanceof ConstraintViolationException) { // hibernate抛出的，处理请求参数或实体格式错误 validate失败后抛出的异常
            ConstraintViolationException ex = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> cs = ex.getConstraintViolations();
            map = new HashMap<>(cs.size());
            for (ConstraintViolation cv : cs) {
                String[] strs = cv.getPropertyPath().toString().split("\\.");
                map.put(strs[strs.length-1], cv.getMessage());
            }
        }
        if (map != null && !map.isEmpty()) {
            return ResponseData.error(ErrorMsg.ARG_VALID_ERROR).setData(map);
        }

        // 权限异常，shiro抛出
        if(e instanceof UnauthorizedException) {
            return ResponseData.error(ErrorMsg.ERROR_PERMISSION);
        }

        // 手动抛出的异常
        if (e instanceof UserException) {
            UserException ex = (UserException) e;
            // 没有ResponseData
            String tip = ex.getMsg();
            if (tip != null) return ResponseData.error(tip);
            /**
             * 有额外ResponseData,直接响应，一般不会用这个，
             * 而是直接返回失败json体
             */
            ResponseData res = ex.getResponseData();
            if (res != null) return res;
            // 未知的手动抛出
            return ResponseData.error(ErrorMsg.ERROR_500);
        }

        // 404,需要在配置文件开启404抛出异常
        if (e instanceof NoHandlerFoundException) {
            return ResponseData.error(ErrorMsg.ERROR_404);
        }

        /// 剩下的未捕获异常

        // 错误原因
        String msg = null;
        // 请求消息不可读异常 - 可获取根异常
        if (e instanceof HttpMessageNotReadableException) {
            Throwable t = ((HttpMessageNotReadableException)e).getRootCause();
            if (null != t)msg = t.getMessage();

            // 请求参数解密错误或者密钥失效，目前使用的加解密拦截只能抛IO异常...所以从IO异常取
            if (t instanceof IOException){
                if (msg.equals(ErrorMsg.REQUEST_DECRYPT_ERROR) || msg.equals(ErrorMsg.SK_EXPIRE_ERROR))
                return ResponseData.error(msg);
            }

        // 其他异常
        } else {
            Throwable t = e.getCause();
            if (null != t)msg = t.getMessage();
        }

        return ResponseData.error(ErrorMsg.ERROR_500).add("tip",null==msg? e.getMessage():msg);
    }

    /**
     * 获取校验错误map
     * @param bindingResult
     * @return
     */
    private Map<String, Object> getBindResultMap(BindingResult bindingResult) {
        Map<String, Object> maps = null;
        if (bindingResult!=null && bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            maps = new HashMap<>(fieldErrors.size());
            for(FieldError error: fieldErrors) {
                maps.put(error.getField(), error.getDefaultMessage());
            }
        }
        return maps;
    }
}
