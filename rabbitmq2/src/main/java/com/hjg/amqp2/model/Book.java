package com.hjg.amqp2.model;

import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
@Data
public class Book {
    private String name;
    private String author;
    private Date publishDate;
}
