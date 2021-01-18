package com.hjg.spring.config.web;

import com.hjg.spring.model.Person;
import org.springframework.core.convert.converter.Converter;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/18
 */
public class PersonConverter implements Converter<String, Person> {
    @Override
    public Person convert(String source) {
        String[] array = source.split("_");

        Person person = new Person();
        person.setName(array[0]);
        person.setAge(Integer.valueOf(array[1]));
        return person;
    }
}
