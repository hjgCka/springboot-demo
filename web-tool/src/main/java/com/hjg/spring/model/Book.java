package com.hjg.spring.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @DateTimeFormat以及@NumberFormat注解都是用在数据绑定，但是在@RequestBody进行序列化时不会被序列化机制所支持。
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

    /**
     * 返回给客户端的值，由默认的formatter即NumberStyleFormatter决定，且没有开放配置属性可以自定义。
     */
    @NumberFormat(pattern = "###,###.##")
    private BigDecimal price;
}
