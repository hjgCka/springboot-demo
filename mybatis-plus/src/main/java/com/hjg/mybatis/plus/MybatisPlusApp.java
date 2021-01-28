package com.hjg.mybatis.plus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/28
 */
@MapperScan(basePackages = {"com.hjg.mybatis.plus.mapper"})
@SpringBootApplication
public class MybatisPlusApp {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApp.class, args);
    }
}
