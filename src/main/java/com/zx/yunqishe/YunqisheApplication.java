package com.zx.yunqishe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

// @Configuration @Component @Controller @Service
@SpringBootApplication(scanBasePackages = {"com.zx.yunqishe.common",
        "com.zx.yunqishe.service","com.zx.yunqishe.controller"})
// @Mapper......
@MapperScan(basePackages = "com.zx.yunqishe.dao")
// @WebFilter @WebListener......
@ServletComponentScan(basePackages = "com.zx.yunqishe.common")
public class YunqisheApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunqisheApplication.class, args);
    }

}
