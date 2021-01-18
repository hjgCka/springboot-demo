package com.hjg.spring.controlller;

import com.hjg.spring.model.Book;
import com.hjg.spring.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping("/return/{jim}/person")
    public Person returnPerson(@ModelAttribute(value = "jim", binding = false) Person person, Model model) {
        logger.info("person = {}", person);
        //这里的person对象来自Model，先进行类型转换再将其放在模型中。
        //通过http://localhost:8081/cka/return/jim_20/person?weight=60访问时，默认使用了数据绑定
        //这时会注入weight字段，如果设置binding=false，weight字段是默认值
        Person person2 = (Person) model.getAttribute("jim");
        return person2;
    }

    //
}
