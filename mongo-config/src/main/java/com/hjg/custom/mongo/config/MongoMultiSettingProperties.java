package com.hjg.custom.mongo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Data
@Validated
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoMultiSettingProperties {

    /**
     * 自定义转换器的包路径，支持多个包路径。
     */
    String[] convertPackages;

    @NotNull
    @NestedConfigurationProperty
    MongoSettingProperties primary;

    @NestedConfigurationProperty
    MongoSettingProperties secondary;

}
