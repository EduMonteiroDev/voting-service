package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.service.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


import static com.sicredi.votingservice.constants.VotingServiceConstants.COMPUTED_VOTE_SUCCESSFULLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class VoteControllerTest {
    @InjectMocks
    private VoteController voteController;
    @Mock
    private VoteService voteService;

    @Test
    public void vote_with_success() {
        var topicId = 123L;
        var voteRequest = VoteRequest.builder().vote(Boolean.TRUE).build();

        var response = voteController.vote(topicId, voteRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(COMPUTED_VOTE_SUCCESSFULLY, response.getBody());
        verify(voteService, times(1)).processVote(topicId, voteRequest);
    }

    @Test
    public void get_results_from_topic_with_success() {
        var topicId = 123L;
        var voteResponse = VoteResponse.builder().build();

        when(voteService.getTopicResult(topicId)).thenReturn(voteResponse);

        var response = voteController.result(topicId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(voteResponse, response.getBody());
        verify(voteService, times(1)).getTopicResult(topicId);
    }
}
