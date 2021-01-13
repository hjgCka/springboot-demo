package com.hjg.spring.controlller;

import com.hjg.spring.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/13
 */
@RestController
@RequestMapping(value = "/cka")
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MMM-dd hh:mm:ss");

    @Autowired
    private Book book;

    @RequestMapping("/return/book")
    public String returnBook() {
        //这个时间每次都不一样，足以证明每次获取的对象不一样。
        LocalDateTime publishDate = book.getPublishDate();
        String str = df.format(publishDate);
        return str;
    }
}
