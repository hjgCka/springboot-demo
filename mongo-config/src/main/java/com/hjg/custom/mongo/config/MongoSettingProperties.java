package com.hjg.custom.mongo.config;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MongoSettingProperties {

    @NotNull
    private String database;

    @NotNull
    @Size(min = 1)
    private String[] hosts;

    @NotNull
    @Size(min = 1)
    private int[] ports;

    private String username;
    private String password;
    private String authenticationDatabase;

    private Integer minConnectionsPerHost = 20;
    private Integer connectionsPerHost = 20;
}
