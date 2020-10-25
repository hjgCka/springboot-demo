package com.hjg.mongodb.dao;

import com.hjg.mongodb.vo.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookDao {

    List<Book> selectAllBooks();

    void insertBook(Book book);

    void deleteBookByTitle(String title);

    Page<Book> pagedQuery(int pageNo, int pageSize);
}
