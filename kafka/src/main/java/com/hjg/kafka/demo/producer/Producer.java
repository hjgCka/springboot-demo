package com.hjg.kafka.demo.producer;

import com.hjg.kafka.demo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/11/26
 */
@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    KafkaTemplate<String, Book> kafkaTemplate;

    @Value("${kafka.topic.name}")
    private String topicName;

    public void send(Book book) {
        ListenableFuture<SendResult<String, Book>> future = kafkaTemplate.send(topicName, book);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Book>>() {

            @Override
            public void onSuccess(SendResult<String, Book> result) {
                logger.info("send(),发送成功,INPUT:book={},RESULT:result={}", book, result);
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("send(),发送失败,INPUT:book={}", book, ex);
            }
        });
    }
}
