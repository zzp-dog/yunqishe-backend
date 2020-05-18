package com.zx.yunqishe.entity.request;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.consts.RegExp;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class SingleUser {

    @Pattern(
            regexp = RegExp.REG_OHER_NAME,
            message = ErrorMsg.ACCOUNT_ERROR)
    private String account;
    @Pattern(
            regexp = RegExp.REG_MD5,
            message = ErrorMsg.PASSWORD_ERROR)
    private String password;
}
