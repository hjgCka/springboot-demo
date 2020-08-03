package com.hjg.spring.redis.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
@Validated
@ConfigurationProperties(prefix = "spring.redis")
public class SingleRedisMultiProperties {

    @NotNull
    @NestedConfigurationProperty
    private SingleRedisProperties primary;

    /**
     * 可选的其它redis数据源
     */
    @NestedConfigurationProperty
    private Map<String, SingleRedisProperties> secondaryMap = new HashMap<>();

}
