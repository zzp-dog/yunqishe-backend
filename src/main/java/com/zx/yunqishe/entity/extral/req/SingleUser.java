package com.zx.yunqishe.entity.extral.req;

import com.zx.yunqishe.common.consts.ErrorMsg;
import com.zx.yunqishe.common.consts.RegExp;
import com.zx.yunqishe.common.utils.ValidatorUtil;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

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
