package com.hjg.spring.redis.config.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Data
@Valid
@ConfigurationProperties(prefix = "spring.redis")
public class ClusterRedisMultiProperties {

    @NotNull
    @NestedConfigurationProperty
    private ClusterRedisProperties primary;

    /**
     * 可选的其它redis数据源
     */
    @NestedConfigurationProperty
    private Map<String, ClusterRedisProperties> secondaryMap = new HashMap<>();
}
