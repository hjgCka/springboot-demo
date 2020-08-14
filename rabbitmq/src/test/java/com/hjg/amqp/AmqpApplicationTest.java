package com.hjg.amqp;

import com.hjg.amqp.conf.ReceiverConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AmqpApplicationTest {

    @MockBean
    private ReceiverConfiguration receiverConfiguration;

    /**
     * 如果定义了一个MessageConverter ，它会自动地关联到自动配置地amqpTemplate。
     */
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    @Qualifier("helloQueue")
    private Queue helloQueue;

    /**
     * 发布消息到普通队列。
     */
    @Test
    public void send2Hello() {
        String message = "hello world ";

        int length = 8;
        for(int i= 0; i<length; i++) {
            //MessagePostProcessor提供了消息被发送前的一个回调，这样可以改message有效负载或header
            this.amqpTemplate.convertAndSend(helloQueue.getName(), message + i,
                    message1 -> {
                        message1.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        return message1;
                    });
        }

        System.out.println("send completed");
    }

    @Autowired
    private FanoutExchange fanoutExchange;
    @Test
    public void send2FanoutExchange() {
        String message = "hello world ";

        int length = 8;
        for(int i= 0; i<length; i++) {
            //这里的第二个参数routingKey并不重要，填写任意值都不影响路由到哪个队列
            this.amqpTemplate.convertAndSend(fanoutExchange.getName(), "333", message + i);
        }
    }

    @Autowired
    private DirectExchange directExchange;
    @Test
    public void send2DirectExchange() {
        String message = "hello world ";

        String[] array = {"orange", "black", "green"};

        int length = 8;
        for(int i= 0; i<length; i++) {
            int index = (int)(Math.random()*10) % array.length;
            String routeKey = array[index];
            this.amqpTemplate.convertAndSend(directExchange.getName(), routeKey, message + routeKey);
        }
    }
}
