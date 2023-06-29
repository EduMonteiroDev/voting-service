package com.sicredi.votingservice.message.producer;

import com.sicredi.votingservice.configuration.VotingServiceConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SessionProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void sendMessage(String topicName, Long yesVotes, Long noVotes){

        rabbitTemplate.convertAndSend(queue.getName(), "Topic = "+topicName+" yes votes= "+yesVotes+" no votes= "+noVotes);
    }
}
