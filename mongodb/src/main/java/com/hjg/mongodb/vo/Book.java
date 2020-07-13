package com.hjg.mongodb.vo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Document(collection = "books")
public class Book {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("author")
    private String author;

    @Field("price")
    private BigDecimal price;

    @Field("press_time")
    private Date pressTime;
}
