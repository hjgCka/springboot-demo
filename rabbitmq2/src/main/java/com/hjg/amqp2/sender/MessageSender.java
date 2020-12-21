package com.hjg.amqp2.sender;

import com.hjg.amqp2.constants.AppConstants;
import com.hjg.amqp2.model.Book;
import com.hjg.amqp2.model.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
@Component
public class MessageSender {
    private static final Logger logger = LoggerFactory.getLogger(MessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendPhoneMessage() {
        Phone phone = new Phone();
        phone.setName("xiaomi");
        phone.setCreateDate(new Date());

        this.rabbitTemplate.convertAndSend(AppConstants.PHONE_QUEUE, phone);

        String threadName = Thread.currentThread().getName();
        logger.info("sendPhoneMessage(),发送完成ThreadName={}", threadName);
    }

    public void sendBookMessage() {
        Book book = new Book();
        book.setName("Thinking in java");
        book.setAuthor("James");
        book.setPublishDate(new Date());

        this.rabbitTemplate.convertAndSend(AppConstants.BOOK_QUEUE, book);

        String threadName = Thread.currentThread().getName();
        logger.info("sendBookMessage(),发送完成ThreadName={}", threadName);
    }
}
