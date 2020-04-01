package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.entity.ResponseData;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(API.ERROR)
public class MyErrorController implements ErrorController {

    /**
     * 获取错误路径
     * @return
     */
    @Override
    public String getErrorPath() {
        return API.ERROR + API.MY_ERROR;
    }

    /**
     * 404错误路径映射
     * @param res
     * @return
     */
    @RequestMapping(API.MY_ERROR)
    public ResponseData error(HttpServletRequest request, HttpServletResponse res) {
        String tip;
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 401){
            tip = ErrorMsg.ERROR_401;
        }else if(statusCode == 404){
            tip = ErrorMsg.ERROR_404;
        }else if(statusCode == 403){
            tip = ErrorMsg.ERROR_403;
        }else{
            tip = ErrorMsg.ERROR_500;
        }
        res.setStatus(200);
        return ResponseData.error(tip);
    }

}