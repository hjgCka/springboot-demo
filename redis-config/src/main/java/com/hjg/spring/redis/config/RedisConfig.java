package com.hjg.spring.redis.config;

import com.hjg.spring.redis.config.type.ClusterRedisConfig;
import com.hjg.spring.redis.config.type.SingleRedisConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Configuration
@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class, RedisRepositoriesAutoConfiguration.class})
@EnableCaching
@Import({SingleRedisConfig.class, ClusterRedisConfig.class})
public class RedisConfig {

    @Bean
    public RedisConfigPostProcessor singleRedisConfigPostProcessor(Environment environment) {
        return new RedisConfigPostProcessor(environment);
    }
}
