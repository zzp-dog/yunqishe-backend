package com.zx.yunqishe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

// @Configuration @Component @Controller @Service
@SpringBootApplication(scanBasePackages = {
        "com.zx.yunqishe.common",
        "com.zx.yunqishe.controller",
        "com.zx.yunqishe.service",
        "com.zx.yunqishe.dao"
})
// @WebFilter @WebListener......
@ServletComponentScan(basePackages = "com.zx.yunqishe.sys")
// dao - mapper
@MapperScan("com.zx.yunqishe.dao")
public class YunqisheApplication {

    public static void main(String[] args) {
        SpringApplication.run(YunqisheApplication.class, args);
    }

}
