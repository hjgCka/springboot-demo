package com.hjg.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisAppTest {

    @Autowired
    private RedisTemplate primary;

    @Resource(name = "secondary1")
    private RedisTemplate secondaryRedisTemplate;

    @Test
    public void test() {

    }
}
