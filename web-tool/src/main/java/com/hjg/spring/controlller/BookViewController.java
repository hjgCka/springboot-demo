package com.hjg.spring.controlller;

import com.hjg.spring.model.Book;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2021/1/22
 */
@Controller
public class BookViewController {

    @ModelAttribute
    public void addBook(Model model) {
        Book book = new Book();
        book.setName("Java");
        book.setPrice(new BigDecimal(500));
        book.setPublishDate(new Date());
        model.addAttribute("book", book);
    }

    @RequestMapping("/book/info")
    public String bookHtml() {
        return "book";
    }
}
