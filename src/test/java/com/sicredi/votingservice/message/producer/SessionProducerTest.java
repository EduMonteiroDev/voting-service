package com.sicredi.votingservice.message.producer;

import com.sicredi.votingservice.model.response.VoteResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionProducerTest {
    @InjectMocks
    private SessionProducer sessionProducer;
    @Mock
    private RabbitTemplate rabbitTemplate;

    @Mock
    private Queue queue;

    @Test
    public void testSendMessage() {
        var topicName = "Test Topic";
        var yesVotes = 10L;
        var noVotes = 5L;

        var voteResponse = VoteResponse.builder()
                .topicName(topicName)
                .yesVotes(yesVotes)
                .noVotes(noVotes)
                .totalVotes(yesVotes + noVotes)
                .build();

        sessionProducer.sendMessage(topicName, voteResponse);

        var expectedMessage = "Topic = "+topicName+" yes votes= "+yesVotes+" no votes= "+noVotes;

        verify(rabbitTemplate).convertAndSend(queue.getName(), expectedMessage);
    }
}
