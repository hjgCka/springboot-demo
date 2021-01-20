package com.hjg.spring.config.web;

import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/13
 */
//@Component
public class MyWebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册自定义类型转换器。
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        //这里注册的converter和formatter，是在全局的FormattingConversionService内
        registry.addConverter(new PersonConverter());
    }

    /**
     * 添加自定义拦截器。
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
