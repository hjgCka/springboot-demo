package com.hjg.spring.model;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/13
 */
@Data
public class Book {
    private String name;
    private String author;
    private Date publishDate = new Date();
}
