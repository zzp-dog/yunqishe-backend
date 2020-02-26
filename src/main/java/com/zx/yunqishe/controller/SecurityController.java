package com.zx.yunqishe.controller;

import com.zx.yunqishe.common.consts.API;
import com.zx.yunqishe.common.utils.CookieUtil;
import com.zx.yunqishe.entity.ResponseData;
import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.exception.UserException;
import com.zx.yunqishe.common.utils.DateUtil;
import com.zx.yunqishe.common.utils.EncryptUtil;
import com.zx.yunqishe.common.utils.ValidatorUtil;
import com.zx.yunqishe.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Base64;

/**
 * 应用安全请求控制器
 */
@Validated
@RestController
@RequestMapping(API.SECURITY)
public class SecurityController {

    @Autowired
    private UserService userService;

    /**
     * 请求公钥
     * @return
     */
    @GetMapping(API.GET_PK)
    public ResponseData getPK() {
        return ResponseData.success()
                .add("pk", Base64.getEncoder().encodeToString(EncryptUtil.getAppPk().getEncoded()));
    }

    /**
     * 上送的前端密钥,后续前后端加解密就用AES加解密方式
     * 接受
     * @return
     */
    @PostMapping(API.SEND_SK)
    public ResponseData sendSK(@RequestBody String o, HttpServletRequest request) throws Exception{
        // sk解密,存在session中
        try {
            String sk = EncryptUtil.RSADecrypt(o, EncryptUtil.getAppSk());
            request.getSession().setAttribute("sk", sk);
        } catch (Exception e) {
            throw new UserException(ErrorMsg.GET_SK_ERROR);
        }
        return ResponseData.success();
    }
}
