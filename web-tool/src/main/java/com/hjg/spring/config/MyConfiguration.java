package com.hjg.spring.config;

import com.hjg.spring.property.SingleRedisMultiProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SingleRedisMultiProperties.class)
public class MyConfiguration {

}
