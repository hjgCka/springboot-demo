package com.hjg.kafka.demo.consumer;

import com.hjg.kafka.demo.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/11/26
 */
@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @Autowired
    KafkaTemplate<String, Book> kafkaTemplate;

    /**
     * id属性用于group.id，它会覆盖consumer factory的值
     * 可以设置@KafkaListener创建得消费者前缀
     * @param book
     */
    @KafkaListener(groupId = "${kafka.topic.group-id}", topics = {"${kafka.topic.name}"},
            topicPartitions = {
                    @TopicPartition(topic = "${kafka.topic.name}", partitions = {"0"}),
                    @TopicPartition(topic = "${kafka.topic.name}", partitions = {"1"}),
                    @TopicPartition(topic = "${kafka.topic.name}", partitions = {"2"})
            },
            clientIdPrefix = "book_consumer")
    public void listen(Book book) {
        logger.info("listen(),INPUT:book={}", book);
    }
}
