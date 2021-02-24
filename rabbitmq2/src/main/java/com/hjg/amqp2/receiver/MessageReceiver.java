package com.hjg.amqp2.receiver;

import com.hjg.amqp2.constants.AppConstants;
import com.hjg.amqp2.model.Book;
import com.hjg.amqp2.model.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
@Component
public class MessageReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    @RabbitListener(queues = AppConstants.PHONE_QUEUE, concurrency = "${queue.phone.concurrency}-${queue.phone.maxConcurrency}")
    public void recordPhone(Phone phone, Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        logger.info("messageProperties = {}", messageProperties);
        logger.info("phone = {}", phone);
    }

    @RabbitListener(queues = AppConstants.BOOK_QUEUE, concurrency = "20-50")
    public void recordBook(Book book, Message message) {
        MessageProperties messageProperties = message.getMessageProperties();
        logger.info("messageProperties = {}", messageProperties);
        logger.info("book = {}", book);
    }
}
