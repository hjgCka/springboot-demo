package com.hjg.spring.config.web;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/13
 */
@Configuration
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

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        //注册ContentNegotiatingViewResolver解析器，并配置默认视图
        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        //禁用路径名扩展
        configurer.favorPathExtension(false);
        //默认是通过请求头的Accept进行内容协商
        configurer.favorParameter(true);
        //默认参数名称是format，通过这个参数确定请求类型
        configurer.parameterName("format");
        //ContentNegotiatingViewResolver通过请求类型，确定不同的响应类型
        configurer.defaultContentType(MediaType.TEXT_HTML);
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("html", MediaType.TEXT_HTML);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        //禁用路径名扩展
        configurer.setUseSuffixPatternMatch(false);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //添加自定义的消息转换器
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .modulesToInstall(new ParameterNamesModule())
                //反序列化时，未知的属性不会导致失败
                .failOnUnknownProperties(false);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        //要使用xml转换功能，需要相应的依赖
        //converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        //将DispatcherServlet的映射路径改为"/"覆盖default servlet
        //但是
        //启用转发给default servlet的功能，default servlet的默认名称是"default"
        configurer.enable();
    }
}
