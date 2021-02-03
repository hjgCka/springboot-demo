package com.hjg.mybatis.plus.service.impl;

import com.hjg.mybatis.plus.mapper.UserMapper;
import com.hjg.mybatis.plus.model.User;
import com.hjg.mybatis.plus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/3
 */
@Repository
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectAllUsers() {
        List<User> list = userMapper.selectList(null);
        logger.info("list = {}", list);
        return list;
    }

    @Override
    public void saveUser(User user) {
        userMapper.insert(user);
    }
}
