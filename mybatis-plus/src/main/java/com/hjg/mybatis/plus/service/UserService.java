package com.hjg.mybatis.plus.service;

import com.hjg.mybatis.plus.model.User;

import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/2/3
 */
public interface UserService {

    List<User> selectAllUsers();

    void saveUser(User user);
}
