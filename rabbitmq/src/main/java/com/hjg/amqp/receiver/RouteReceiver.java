package com.hjg.amqp.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RouteReceiver {

    @RabbitListener(queues = "#{autoDeleteQueue3.name}")
    public void receive1(String in) {
        //orange green
        this.receive(in, 3);
    }

    @RabbitListener(queues = "#{autoDeleteQueue4.name}")
    public void receive2(String in) {
        //black
        this.receive(in, 4);
    }

    private void receive(String in, int receiver) {
        System.out.println("instance [" + receiver + "], in = '" + in + "'");
    }
}
