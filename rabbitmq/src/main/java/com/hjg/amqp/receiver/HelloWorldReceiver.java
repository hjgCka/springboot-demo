package com.hjg.amqp.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * RabbitListener这个注解也可以直接用于方法。
 */
@RabbitListener(queues = "hello")
public class HelloWorldReceiver {

    private int instanceId;

    public HelloWorldReceiver(int instanceId) {
        this.instanceId = instanceId;
    }

    @RabbitHandler
    public void receive(String in) {
        System.out.println("instanceId[" + instanceId + "]  received '" + in + "'");
    }
}
