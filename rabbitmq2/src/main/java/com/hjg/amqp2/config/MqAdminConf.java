package com.hjg.amqp2.config;

import com.hjg.amqp2.constants.AppConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
public class MqAdminConf {
    @Bean
    public Queue phoneQueue() {
        return new Queue(AppConstants.PHONE_QUEUE);
    }

    @Bean
    public Queue bookQueue() {
        return new Queue(AppConstants.BOOK_QUEUE);
    }
}
