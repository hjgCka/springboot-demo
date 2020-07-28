package com.hjg.spring.property;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class SingleRedisProperties {

    /**
     * 数据源(RedisTemplate)的名称
     */
    @NotBlank
    private String name;

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
     * spring-cache的key，键的失效时间
     */
    private Integer cacheableSecond;
    /**
     *自定义key的有效时间
     */
    private Map<String, Integer> cacheablelist;
}
