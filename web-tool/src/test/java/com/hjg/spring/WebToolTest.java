package com.hjg.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hjg.spring.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/20
 */
@SpringBootTest
public class WebToolTest {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    FormattingConversionService formattingConversionService;

    @Autowired
    DefaultFormattingConversionService defaultService;

    @Autowired
    ThymeleafViewResolver viewResolver;

    @Autowired
    ContentNegotiatingViewResolver contentResolver;

    @Test
    public void converterTest() {
        //通过执行这个测试用例，可知实际上Spring MVC还是使用了DefaultFormattingConversionService
        //并将其作为Spring Bean

        //名称为mvcConversionService
        //如果想使用Java配置类型转换，可以继承WebMvcConfigurationSupport 并覆盖mvcConversionService()方法
        String[] array = applicationContext.getBeanNamesForType(DefaultFormattingConversionService.class);
        Arrays.stream(array).forEach(e -> System.out.println(e));

        String personStr = "Jack_22";
        Person person = defaultService.convert(personStr, Person.class);
        System.out.println(person);
    }

    @Test
    public void viewResolverTest() {
        Map<String, ViewResolver> map = applicationContext.getBeansOfType(ViewResolver.class);
        map.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey());
            System.out.println(e.getValue().getClass());
        });
    }

    @Test
    public void thymeTest() {
        int order = contentResolver.getOrder();
        System.out.println(order);

        String contentType = viewResolver.getContentType();
        System.out.println(contentType);
    }

    @Test
    public void contentResolverTest() {
        List<ViewResolver> list = contentResolver.getViewResolvers();
        list.stream().forEach(e -> System.out.println(e.getClass()));
    }

    @Test
    public void httpConverterTest() throws JsonProcessingException {
        Map<String, HttpMessageConverter> map = applicationContext.getBeansOfType(HttpMessageConverter.class);
        map.entrySet().stream().forEach(e -> {
            System.out.println(e.getKey());
            System.out.println(e.getValue().getClass());
        });

        Person person = new Person();
        person.setBirthDay(new Date());

        MappingJackson2HttpMessageConverter jsonConverter = applicationContext.getBean(MappingJackson2HttpMessageConverter.class);
        String json = jsonConverter.getObjectMapper().writeValueAsString(person);
        System.out.println(json);
    }

}
