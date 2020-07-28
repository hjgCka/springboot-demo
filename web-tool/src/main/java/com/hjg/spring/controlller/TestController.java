package com.hjg.spring.controlller;

import com.hjg.spring.property.SingleRedisMultiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private SingleRedisMultiProperties singleRedisMultiProperties;

    @RequestMapping(value = "/view/primary/property", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object viewPrimaryProperty() {
        return singleRedisMultiProperties.getPrimary();
    }

    @RequestMapping(value = "/view/secondary/property", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Object viewSecondaryProperty() {
        return singleRedisMultiProperties.getSecondaries();
    }
}
