package com.chenze.projectadvancementdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.chenze.projectadvancementdemo.model.dao")
public class ProjectAdvancementDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectAdvancementDemoApplication.class, args);
    }

}
