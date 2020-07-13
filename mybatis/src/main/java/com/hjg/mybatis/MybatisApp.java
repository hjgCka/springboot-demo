package com.hjg.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan({"com.hjg.mybatis.mapper"})
@SpringBootApplication
public class MybatisApp {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApp.class, args);
    }
}
