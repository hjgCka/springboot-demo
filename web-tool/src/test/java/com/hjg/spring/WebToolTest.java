package com.hjg.spring;

import com.hjg.spring.model.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.util.Arrays;

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

}
