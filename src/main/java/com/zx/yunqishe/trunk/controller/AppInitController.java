package com.zx.yunqishe.trunk.controller;

import com.zx.yunqishe.trunk.entity.ResponseData;
import com.zx.yunqishe.trunk.other.exception.UserException;
import com.zx.yunqishe.trunk.other.utils.EncryptUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * app启动初始化请求控制器
 */
@RestController
public class AppInitController {

    /**
     * 请求公钥secure
     * @return
     */
    @GetMapping("/security/getpk")
    public ResponseData getPK() {
        return ResponseData.success()
                .add("pk", Base64.getEncoder().encodeToString(EncryptUtil.getAppPk().getEncoded()));
    }

    /**
     * 上送的前端密钥,后续前后端加解密就用AES加解密方式
     * 接受
     * @return
     */
    @PostMapping("/security/sendsk")
    public ResponseData sendSK(@RequestBody String o, HttpServletRequest request) throws Exception{
        // sk解密,存在session中
        try {
            String sk = EncryptUtil.RSADecrypt(o, EncryptUtil.getAppSk());
            request.getSession().setAttribute("sk", sk);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserException("密钥协商失败");
        }
        return ResponseData.success();
    }
}