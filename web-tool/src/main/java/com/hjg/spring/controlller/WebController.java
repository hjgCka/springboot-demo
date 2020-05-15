package com.hjg.spring.controlller;


import com.hjg.spring.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
        logger.info("");

        User user = new User();
        user.setAge(22);
        user.setMobile("13500008888");
        user.setName("James");
        return user;
    }

    @RequestMapping("/get/properties")
    public String getProperties() {

        Properties properties = System.getProperties();

        return properties.toString();
    }
}
