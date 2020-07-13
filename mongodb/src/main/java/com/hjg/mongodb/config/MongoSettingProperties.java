package com.hjg.mongodb.config;

import lombok.Data;

import javax.validation.Valid;

@Data
@Valid
public class MongoSettingProperties {

    private String[] hosts;
    private int[] ports;
    private String username;
    private String password;

    //要使用的数据库
    private String database;

    //鉴权用数据库
    private String authenticationDatabase;
}
