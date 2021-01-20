package com.hjg.spring.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate = new Date();

    @NumberFormat(pattern = "###,###.##")
    private BigDecimal price;
}
