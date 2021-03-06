package com.hjg.spring.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/18
 */
@Data
public class Person {
    String name;
    int age;
    int weight;
    Date birthDay;
}
