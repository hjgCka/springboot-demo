package com.hjg.spring.redis.config.util;

import com.hjg.spring.redis.config.model.ClusterRedisProperties;
import com.hjg.spring.redis.config.model.SingleRedisProperties;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisUtils {

    public static RedisTemplate<Object, Object> createRedisTemplate(@NotNull LettuceConnectionFactory lettuceConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = getJsonSerializer();
        //配置redisTemplate
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        RedisSerializer<?> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);//key序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);//value序列化
        redisTemplate.setHashKeySerializer(stringSerializer);//Hash key序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);//Hash value序列化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public static Jackson2JsonRedisSerializer getJsonSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //反序列化时，如果缓存中存储的值属性多余Java对象，这时不要失败
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }

    public static LettuceConnectionFactory createSingleConnectionFactory(@NotNull SingleRedisProperties singleRedisProperties){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxWaitMillis(singleRedisProperties.getMaxWait());
        config.setMaxIdle(singleRedisProperties.getMaxIdle());
        config.setMinIdle(singleRedisProperties.getMinIdle());
        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .commandTimeout(Duration.ofMillis(singleRedisProperties.getTimeout()))
                .build();
        RedisStandaloneConfiguration singleConfiguration = new RedisStandaloneConfiguration();
        singleConfiguration.setHostName(singleRedisProperties.getHost());
        singleConfiguration.setPort(singleRedisProperties.getPort());
        singleConfiguration.setDatabase(singleRedisProperties.getDatabase());
        singleConfiguration.setPassword(RedisPassword.of(singleRedisProperties.getPassword()));
        LettuceConnectionFactory factory = new LettuceConnectionFactory(singleConfiguration, pool);
        //经过测试，手动注册的bean必须要调用此方法初始化，否则会secondary数据源无法正常工作
        factory.afterPropertiesSet();
        return factory;
    }

    public static LettuceConnectionFactory createClusterConnectionFactory(ClusterRedisProperties clusterRedisProperties){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxWaitMillis(clusterRedisProperties.getMaxWait());
        config.setMaxIdle(clusterRedisProperties.getMaxIdle());
        config.setMinIdle(clusterRedisProperties.getMinIdle());
        LettucePoolingClientConfiguration pool = LettucePoolingClientConfiguration.builder()
                .poolConfig(config)
                .commandTimeout(Duration.ofMillis(clusterRedisProperties.getTimeout()))
                .build();
        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration();
        Set<RedisNode> nodes = new HashSet<RedisNode>();
        for(String address : clusterRedisProperties.getNodes()){
            String[] ipAndPort = address.split(":");
            nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
        }
        clusterConfiguration.setClusterNodes(nodes);
        clusterConfiguration.setPassword(RedisPassword.of(clusterRedisProperties.getPassword()));
        clusterConfiguration.setMaxRedirects(clusterRedisProperties.getMaxRedirects());
        LettuceConnectionFactory factory = new LettuceConnectionFactory(clusterConfiguration, pool);
        factory.afterPropertiesSet();
        return factory;
    }

    //**********用于创建spring-cache的CacheManager******************

    public static RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Integer seconds) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(
                RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(jackson2JsonRedisSerializer)
        ).entryTtl(Duration.ofSeconds(seconds));

        return redisCacheConfiguration;
    }

    public static Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap(Map<String,Integer> cacheablelist) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();

        if(cacheablelist!=null) {
            for (Map.Entry<String, Integer> entry : cacheablelist.entrySet()) {
                redisCacheConfigurationMap.put(entry.getKey(), RedisUtils.getRedisCacheConfigurationWithTtl(entry.getValue()));
            }
        }
        return redisCacheConfigurationMap;
    }

}
