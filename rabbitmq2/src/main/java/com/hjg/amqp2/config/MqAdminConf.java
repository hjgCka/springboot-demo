package com.hjg.amqp2.config;

import com.hjg.amqp2.constants.AppConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
@Configuration
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
