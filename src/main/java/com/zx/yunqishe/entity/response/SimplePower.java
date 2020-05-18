package com.zx.yunqishe.entity.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimplePower {
    /** 主键 */
    private Integer id;
    /**  父权限id */
    private Integer pid;
    /** 描述 */
    private String description;
}
