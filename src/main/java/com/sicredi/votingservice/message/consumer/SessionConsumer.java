package com.sicredi.votingservice.message.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionConsumer {

    @RabbitListener(queues = {"${spring.rabbitmq.queue-name}"})
    public void receive(@Payload String fileBody) {
        System.out.println("Result: " + fileBody);
    }

}
