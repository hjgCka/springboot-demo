package com.hjg.spring.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = "spring.redis")
public class SingleRedisMultiProperties {

    @NotNull
    @NestedConfigurationProperty
    private SingleRedisProperties primary;

    @Size(min = 0)
    @NestedConfigurationProperty
    private List<SingleRedisProperties> secondaries = new ArrayList<>();
}
