package com.hjg.mongodb;

import com.hjg.mongodb.dao.BookDao;
import com.hjg.mongodb.vo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

/**
 * 使用@SpringBootTest的properties属性也可以设置profile。
 */
@SpringBootTest
@ActiveProfiles("dev")
public class MongodbAppTest {

    @Autowired
    private BookDao bookDao;

    @Test
    public void findTest(){
        List<Book> books = bookDao.selectAllBooks();
        System.out.println("books = " + books);
    }
}
