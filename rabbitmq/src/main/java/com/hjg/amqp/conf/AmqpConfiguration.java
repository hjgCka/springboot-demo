package com.hjg.amqp.conf;

import org.springframework.amqp.core.*;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfiguration {

    /**
     * 重试是默认禁用的。你也可以像这样用程序自定义RetryTemplate。
     * @return
     */
    @Bean
    RabbitRetryTemplateCustomizer rabbitRetryTemplateCustomizer() {
        return (target, retryTemplate) -> {

        };
    }

    @Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    /**
     * 配置发布/订阅的实体。
     * @return
     */
    private class SubscribeConfig {
        @Bean
        public FanoutExchange fanoutExchange() {
            return new FanoutExchange("my.fanout");
        }
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }
        @Bean
        public Queue autoDeleteQueue2() {
            //non-durable, exclusive, auto-delete
            return new AnonymousQueue();
        }
        @Bean
        public Binding binding1(FanoutExchange fanoutExchange, Queue autoDeleteQueue1) {
            //fanout类型的exchange与队列的binding-key并不重要
            return BindingBuilder.bind(autoDeleteQueue1).to(fanoutExchange);
        }
        @Bean
        public Binding binding2(FanoutExchange fanoutExchange, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanoutExchange);
        }
    }

    /**
     * 路由用配置。
     */
    private class RouteConfig {
        @Bean
        public DirectExchange directExchange() {
            return new DirectExchange("my.direct");
        }
        @Bean
        public Queue autoDeleteQueue3() {
            return new AnonymousQueue();
        }
        @Bean
        public Queue autoDeleteQueue4() {
            //non-durable, exclusive, auto-delete
            return new AnonymousQueue();
        }
        @Bean
        public Binding binding1a(DirectExchange directExchange, Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3).to(directExchange).with("orange");
        }
        @Bean
        public Binding binding2a(DirectExchange directExchange, Queue autoDeleteQueue4) {
            return BindingBuilder.bind(autoDeleteQueue4).to(directExchange).with("black");
        }
        @Bean
        public Binding binding1b(DirectExchange directExchange, Queue autoDeleteQueue3) {
            return BindingBuilder.bind(autoDeleteQueue3).to(directExchange).with("green");
        }
        @Bean
        public Binding binding2b(DirectExchange directExchange, Queue autoDeleteQueue4) {
            return BindingBuilder.bind(autoDeleteQueue4).to(directExchange).with("black");
        }
    }

}
