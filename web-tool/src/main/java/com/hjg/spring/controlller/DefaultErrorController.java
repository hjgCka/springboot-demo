package com.hjg.spring.controlller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/27
 */
@RestController
public class DefaultErrorController {

    @RequestMapping("/errors")
    public Map<String, Object> defaultErrorHandler(HttpServletRequest request) {
        //在使用springboot时，配置/error会发生冲突
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));
        map.put("message", "发生了一些错误");
        return map;
    }
}
