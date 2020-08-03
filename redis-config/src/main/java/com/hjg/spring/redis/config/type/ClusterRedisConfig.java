package com.hjg.spring.redis.config.type;

import com.hjg.spring.redis.config.model.ClusterRedisMultiProperties;
import com.hjg.spring.redis.config.model.ClusterRedisProperties;
import com.hjg.spring.redis.config.util.RedisUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;

@Profile({"prod"})
@Configuration
@EnableConfigurationProperties({ClusterRedisMultiProperties.class})
public class ClusterRedisConfig extends CachingConfigurerSupport {

    @Bean
    @Primary
    public RedisTemplate primaryRedisTemplate(LettuceConnectionFactory primaryLettuceConnectionFactory) {
        return RedisUtils.createRedisTemplate(primaryLettuceConnectionFactory);
    }

    @Bean
    @Primary
    public LettuceConnectionFactory primaryLettuceConnectionFactory(ClusterRedisMultiProperties clusterRedisMultiProperties) {
        ClusterRedisProperties primary = clusterRedisMultiProperties.getPrimary();
        return RedisUtils.createClusterConnectionFactory(primary);
    }

    @Bean
    @Primary
    public CacheManager cacheManager(LettuceConnectionFactory lettuceConnectionFactory, ClusterRedisMultiProperties clusterRedisMultiProperties) {
        ClusterRedisProperties primary = clusterRedisMultiProperties.getPrimary();
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(lettuceConnectionFactory),
                RedisUtils.getRedisCacheConfigurationWithTtl(primary.getCacheableSecond()), // 默认策略，未配置的 key 会使用这个
                RedisUtils.getRedisCacheConfigurationMap(primary.getCacheablelist()) // 指定 key 策略
        );
    }


    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }
}
