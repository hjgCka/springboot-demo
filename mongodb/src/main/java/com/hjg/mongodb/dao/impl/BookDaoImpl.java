package com.hjg.mongodb.dao.impl;

import com.hjg.mongodb.dao.BookDao;
import com.hjg.mongodb.vo.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<Book> pagedQuery(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Query query = new Query(Criteria.where("title").not().is(null));

        long count = this.mongoTemplate.count(query, Book.class);
        List<Book> list = this.mongoTemplate.find(query.with(pageable), Book.class);
        return new PageImpl<>(list, pageable, count);
    }
}
