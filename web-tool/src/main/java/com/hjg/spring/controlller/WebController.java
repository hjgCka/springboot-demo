package com.hjg.spring.controlller;


import com.hjg.spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;


@RestController
@RequestMapping(value = "/cka")
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private Environment environment;

    @RequestMapping("/get/user")
    public User getUser(String name) {
        logger.info("name={}", name);

        User user = new User();
        user.setAge(22);
        user.setMobile("13500008888");
        user.setName("James");
        return user;
    }

    /**
     * 要求请求头的accept值，支持produces设置的值；
     * 并且响应头的content-type还会被设置为对应值。
     * @param user
     * @return
     */
    @RequestMapping(value = "/save/user", produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveUser(User user) {
        logger.info("user={}", user);
        return "success";
    }

    @RequestMapping("/get/properties")
    public String getProperties() {

        Properties properties = System.getProperties();

        return properties.toString();
    }
}
