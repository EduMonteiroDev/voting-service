package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.TopicRequest;
import com.sicredi.votingservice.service.TopicService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static com.sicredi.votingservice.constants.VotingServiceConstants.CREATE_TOPIC_SUCCESSFULLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TopicControllerTest {
    @InjectMocks
    private TopicController topicController;
    @Mock
    private TopicService topicService;

    @Test
    void create_topic_test_with_success() {
        var topicName = "TEST";
        var topicRequest = TopicRequest.builder().topicName(topicName).build();

        var responseEntity = topicController.createTopic(topicRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(CREATE_TOPIC_SUCCESSFULLY, responseEntity.getBody());

        verify(topicService).insertNewTopic(topicName);
    }
}
