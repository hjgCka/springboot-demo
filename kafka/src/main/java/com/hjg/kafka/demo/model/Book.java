package com.hjg.kafka.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/11/26
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String name;
    private String author;
    private Date publishDate;
}
