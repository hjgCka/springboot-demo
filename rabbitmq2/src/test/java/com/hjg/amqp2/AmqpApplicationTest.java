package com.hjg.amqp2;

import com.hjg.amqp2.receiver.MessageReceiver;
import com.hjg.amqp2.sender.MessageSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

/**
 * @description:
 * @author: hjg
 * @createdOn: 2020/12/21
 */
@SpringBootTest
public class AmqpApplicationTest {

    @Autowired
    MessageSender messageSender;

    @MockBean
    MessageReceiver messageReceiver;

    @Test
    public void sendMsgTest() {
        for(int i=0; i<30; i++) {
            messageSender.sendPhoneMessage();
            messageSender.sendBookMessage();
        }
    }
}
