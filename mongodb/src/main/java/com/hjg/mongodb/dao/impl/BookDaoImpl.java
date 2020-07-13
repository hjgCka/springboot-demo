package com.hjg.mongodb.dao.impl;

import com.hjg.mongodb.dao.BookDao;
import com.hjg.mongodb.vo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookDaoImpl implements BookDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Book> selectAllBooks() {
        return mongoTemplate.findAll(Book.class);
    }

    @Override
    public void insertBook(Book book) {
        mongoTemplate.insert(book);
    }

    @Override
    public void deleteBookByTitle(String title) {
        Query query = new Query(Criteria.where("title").is(title));
        mongoTemplate.remove(query, Book.class);
    }
}
