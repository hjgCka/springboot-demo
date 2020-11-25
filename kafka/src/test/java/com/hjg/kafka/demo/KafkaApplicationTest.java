package com.hjg.kafka.demo;

import com.hjg.kafka.demo.consumer.Consumer;
import com.hjg.kafka.demo.model.Book;
import com.hjg.kafka.demo.producer.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/11/26
 */
@SpringBootTest
public class KafkaApplicationTest {

    @MockBean
    Consumer consumer;

    @Autowired
    Producer producer;

    @Test
    public void sendTest() {
        Book book1 = new Book();
        book1.setAuthor("Jack");
        book1.setName("Thinking in Java");
        book1.setPublishDate(new Date());
        producer.send(book1);

        Book book2 = new Book();
        book2.setAuthor("Tom");
        book2.setName("Java Core");
        book2.setPublishDate(new Date());
        producer.send(book2);

        Book book3 = new Book();
        book3.setAuthor("James");
        book3.setName("Spring in action");
        book3.setPublishDate(new Date());

        producer.send(book3);
    }
}
