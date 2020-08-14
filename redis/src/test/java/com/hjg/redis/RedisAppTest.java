package com.hjg.redis;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
public class RedisAppTest {

    @Autowired
    private RedisTemplate primary;

    @Resource(name = "secondary1")
    private RedisTemplate secondaryRedisTemplate;

    @Test
    public void test() {

    }
}
