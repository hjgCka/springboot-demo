package com.hjg.mongodb.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Data
@Valid
@Component
@ConfigurationProperties(prefix = "spring.data.mongodb")
public class MongoMultiSettingProperties {
    private MongoSettingProperties custom;
}
