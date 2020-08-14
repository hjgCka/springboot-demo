package com.hjg.amqp.conf;

import com.hjg.amqp.receiver.HelloWorldReceiver;
import com.hjg.amqp.receiver.RouteReceiver;
import com.hjg.amqp.receiver.SubscribeReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverConfiguration {

    @Bean
    public HelloWorldReceiver helloWorldReceiver1() {
        return new HelloWorldReceiver(1);
    }
    @Bean
    public HelloWorldReceiver helloWorldReceiver2() {
        return new HelloWorldReceiver(2);
    }

    @Bean
    public SubscribeReceiver subscribeReceiver() {
        return new SubscribeReceiver();
    }

    @Bean
    public RouteReceiver routeReceiver() {
        return new RouteReceiver();
    }
}
