package com.chenze.projectadvancementdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan(basePackages = "com.chenze.projectadvancementdemo.model.dao")
@EnableSwagger2
@EnableCaching
public class ProjectAdvancementDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectAdvancementDemoApplication.class, args);
    }

}
