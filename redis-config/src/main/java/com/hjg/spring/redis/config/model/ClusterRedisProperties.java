package com.hjg.spring.redis.config.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
public class ClusterRedisProperties {

    @NotEmpty
    private List<String> nodes;

    private String password;
    /**
     * redis命令执行的超时时间，单位毫秒
     */
    private Integer timeout;
    /**
     *
     */
    private Integer maxRedirects;
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
