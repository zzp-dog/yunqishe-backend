package com.zx.yunqishe.protal.user.controller;

import com.zx.yunqishe.common.annotation.Decrypt;
import com.zx.yunqishe.common.entity.ResponseData;
import com.zx.yunqishe.common.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Decrypt
    @PostMapping("/login")
    public ResponseData login(@RequestBody User user) {
        
        return ResponseData.success("登录成功");
    }
}
