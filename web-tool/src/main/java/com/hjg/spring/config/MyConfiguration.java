package com.hjg.spring.config;

import com.hjg.spring.property.SingleRedisMultiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

    @Bean
    SingleRedisMultiProperties singleRedisMultiProperties() {
        return new SingleRedisMultiProperties();
    }
}
