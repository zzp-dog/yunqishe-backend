package com.zx.yunqishe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "com.zx.yunqishe.trunk.dao")
public class YunqisheApplication {

	public static void main(String[] args) {
		SpringApplication.run(YunqisheApplication.class, args);
	}

}
