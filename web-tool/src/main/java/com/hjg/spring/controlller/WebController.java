package com.hjg.spring.controlller;


import com.hjg.spring.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Properties;


@RestController
@RequestMapping(value = "/cka")
@Api(tags = "查看用户的接口")
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private Environment environment;

    @ApiOperation("通过名称获取user")
    @RequestMapping(value = "/get/user", method = {RequestMethod.GET, RequestMethod.POST})
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
    @ApiOperation("保存user")
    @RequestMapping(value = "/save/user", method = {RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public String saveUser(User user) {
        logger.info("user={}", user);
        return "success";
    }

    @ApiIgnore
    @ApiOperation("获取虚拟机属性")
    @RequestMapping(value = "/get/properties", method = {RequestMethod.GET, RequestMethod.POST})
    public String getProperties() {

        Properties properties = System.getProperties();

        return properties.toString();
    }
}
