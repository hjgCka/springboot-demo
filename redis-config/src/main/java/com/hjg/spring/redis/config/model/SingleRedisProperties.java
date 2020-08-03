package com.hjg.spring.redis.config.model;

import lombok.Data;

import java.util.Map;

@Data
public class SingleRedisProperties {

    private String host;
    private Integer port;
    private String password;
    private Integer database;
    /**
     * redis命令执行的超时时间，单位毫秒
     */
    private Integer timeout;

    private Integer maxIdle;
    private Integer minIdle;
    private Integer maxActive;
    /**
     * 从连接池中获取对象时的超时时间，单位毫秒。
     */
    private Integer maxWait;

    /**
     * CacheManager的键的失效时间，单位秒。
     */
    private Integer cacheableSecond;
    /**
     *CacheManager指定某些key的有效时间，单位秒。
     */
    private Map<String, Integer> cacheablelist;
}
