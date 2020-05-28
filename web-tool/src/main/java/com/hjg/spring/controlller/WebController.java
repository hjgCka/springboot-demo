package com.hjg.spring.controlller;


import com.hjg.spring.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequestMapping(value = "/cka")
@Api(tags = "查看用户的接口")
public class WebController {

    private static final Logger logger = LoggerFactory.getLogger(WebController.class);

    @Autowired
    private Environment environment;

    //设置环境变量APP_NAME=WebTool，下面2种方法都能正确注入
    //@Value("${app.name}")
    //下面这种方法能够注入
    @Value("${app.name}")
    private String appName;

    @Value("${common.name}")
    private String commonName;

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
    @RequestMapping(value = "/get/properties", method = {RequestMethod.GET, RequestMethod.POST}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object getProperties() {

        /*Properties properties = System.getProperties();

        Map<String, String> map = new HashMap<>();

        Enumeration keys = properties.propertyNames();
        while (keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            map.put(key, properties.getProperty(key));
        }

        map.put("appName222", appName);*/

        return appName + commonName;
    }
}
