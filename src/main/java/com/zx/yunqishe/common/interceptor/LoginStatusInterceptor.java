package com.zx.yunqishe.common.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.utils.CookieUtil;
import com.zx.yunqishe.controller.SecurityController;
import com.zx.yunqishe.entity.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LoginStatusInterceptor implements HandlerInterceptor {

    @Autowired
    public SecurityController securityController;
    // 应该是从数据查的，先写死
//    public static final String[] OPEN_APIS = {
//            API.SECURITY + API.GET_PK,
//            API.SECURITY + API.SEND_SK,
//            API.SECURITY + API.VERIFY_TOKEN,
//            API.USER + API.SEND_CODE,
//            API.USER + API.VERIFY_CODE,
//            API.USER + API.QUERY_ADMIN,
//            API.USER + API.REGIST,
//            API.USER + API.INSTALL,
//            API.USER + API.LOGIN,
//            API.USER + API.LOGIN_FOR_SYS,
//            API.USER + API.LOGOUT
//    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        // 可在应用启动后或加载servlet时发sql查开放的api
//        // 开放api直接通行
//        String url = request.getRequestURI();
//        for (String api: OPEN_APIS) {
//            if (api.equals(url))
//                return true;
//        }
//        // 非开放api
//        ResponseData rd;
//        HttpSession session  = request.getSession();
//        Integer uid = (Integer)session.getAttribute("uid");
//        Integer rid = (Integer)session.getAttribute("rid");
//        // 非开放api
//        // 1.先检查用户id和rid,判断是否已登录
//        // 是否带非开放系统管理api的后缀
//        boolean isSysAPI = url.endsWith(API.SUFFIX_SYS);
//        // 非系统管理api
//        if (!isSysAPI && uid != null && rid != null) {
//            return true;
//        }
//        // 非开放系统管理api只允许超级管理员访问
//        if (isSysAPI && uid != null && rid == 3) {
//            return true;
//        }
//        // 2.再检查token，判断是否过期
//        try {
//            rd = securityController.verifyToken(null, request);
//            if (rd.getStatus() == 200) return true;
//        } catch (Exception e) {
//            rd = ResponseData.error(ErrorMsg.KEEP_LOGIN_ERROR);
//        }
//        // 3.响应失败结果
//        ajaxResponse(rd, response);
//        return false;
    }

    /**
     * 登录超时，重新登录
     * @param rd
     * @param res
     */
    private void ajaxResponse(ResponseData rd, HttpServletResponse res) throws IOException {
        String msg = new ObjectMapper().writeValueAsString(rd);
        //必须得到输出流之前设置，否则无效
        res.setContentType("application/json; charset=utf-8");
        //使用字符流
        PrintWriter out=res.getWriter();
        //使用out.write打印null时会报空指针异常
        out.print(msg);
        out.flush();
    }
}
