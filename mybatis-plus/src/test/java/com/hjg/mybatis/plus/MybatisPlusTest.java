package com.hjg.mybatis.plus;

import com.hjg.mybatis.plus.mapper.UserMapper;
import com.hjg.mybatis.plus.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/28
 */
@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void userMapperTest() {
        List<User> list = userMapper.selectList(null);
        list.stream().forEach(e -> System.out.println(e));
    }
}
