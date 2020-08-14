package com.hjg.amqp.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class SubscribeReceiver {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) {
        this.receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) {
        this.receive(in, 2);
    }

    private void receive(String in, int receiver) {
        System.out.println("instance [" + receiver + "], in = '" + in + "'");
    }

}
