package com.sicredi.votingservice.message.producer;

import com.sicredi.votingservice.model.response.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SessionProducer {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void sendMessage(String topicName, VoteResponse voteResponse){

        rabbitTemplate.convertAndSend(queue.getName(), "Topic = "+topicName+" yes votes= "+voteResponse.getYesVotes()+" no votes= "+voteResponse.getNoVotes());
    }
}
