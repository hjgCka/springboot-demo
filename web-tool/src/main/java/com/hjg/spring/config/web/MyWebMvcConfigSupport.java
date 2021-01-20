package com.hjg.spring.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 *
 * 使用这个类时，需要移除@EnableWebMvc，通过javadoc-api可知。
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/20
 */
@Configuration
public class MyWebMvcConfigSupport extends WebMvcConfigurationSupport {

    /**
     * 这个方法其实是加上了@Bean注解，重写方法时也继承了@Bean注解。
     * 所以这个类需要加上@Configuration
     * @return
     */
    @Override
    public FormattingConversionService mvcConversionService() {

        //默认为true，即注册默认的转换器
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
        conversionService.addConverter(new PersonConverter());

        return conversionService;
    }

}
