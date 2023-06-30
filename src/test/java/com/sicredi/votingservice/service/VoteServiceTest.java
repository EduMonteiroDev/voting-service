package com.sicredi.votingservice.service;

import com.sicredi.votingservice.exception.BusinessServiceException;
import com.sicredi.votingservice.exception.DatabaseException;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.model.entity.VoteEntity;
import com.sicredi.votingservice.model.request.VoteRequest;
import com.sicredi.votingservice.repository.VoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {
    @InjectMocks
    private VoteService voteService;
    @Mock
    private TopicService topicService;
    @Mock
    private SessionService sessionService;
    @Mock
    private VoteRepository voteRepository;

    @Test
    public void process_vote_and_compute_with_success() {
        var voteRequest = VoteRequest.builder().build();
        var topicEntity = TopicEntity.builder().build();

        when(topicService.findById(1L)).thenReturn(Optional.of(topicEntity));
        when(voteRepository.existsByDocumentAndTopic(voteRequest.getDocument(), topicEntity)).thenReturn(false);

        voteService.processVote(1L, voteRequest);

        verify(sessionService, times(1)).validateIfSessionExists(topicEntity);
        verify(voteRepository).save(any(VoteEntity.class));
    }

    @Test
    public void process_vote_with_invalid_topic_and_throw_BusinessException() {
        var voteRequest = VoteRequest.builder().build();

        when(topicService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessServiceException.class, ()-> voteService.processVote(1L, voteRequest));
    }

    @Test
    public void process_vote_with_invalid_document_and_throw_BusinessException() {
        var voteRequest = VoteRequest.builder().build();
        var topicEntity = TopicEntity.builder().build();
        when(topicService.findById(1L)).thenReturn(Optional.of(topicEntity));
        when(voteRepository.existsByDocumentAndTopic(voteRequest.getDocument(), topicEntity)).thenReturn(true);

        assertThrows(BusinessServiceException.class, ()-> voteService.processVote(1L, voteRequest));
    }

    @Test
    public void process_vote_and_throw_DatabaseException() {
        var voteRequest = VoteRequest.builder().build();
        var topicEntity = TopicEntity.builder().build();

        when(topicService.findById(1L)).thenReturn(Optional.of(topicEntity));
        when(voteRepository.existsByDocumentAndTopic(voteRequest.getDocument(), topicEntity)).thenReturn(false);
        doThrow(RuntimeException.class).when(voteRepository).save(any(VoteEntity.class));

        assertThrows(DatabaseException.class, ()-> voteService.processVote(1L, voteRequest));
        verify(sessionService, times(1)).validateIfSessionExists(topicEntity);
    }

    @Test
    public void get_topic_result_with_success() {
        var topicEntity = TopicEntity.builder().build();
        when(topicService.findById(1L)).thenReturn(Optional.of(topicEntity));
        when(voteRepository.countByTopicAndVoteTrue(topicEntity)).thenReturn(5L);
        when(voteRepository.countByTopicAndVoteFalse(topicEntity)).thenReturn(3L);

        var voteResponse = voteService.getTopicResult(1L);

        assertEquals(topicEntity.getTopicName(), voteResponse.getTopicName());
        assertEquals(5L, voteResponse.getYesVotes());
        assertEquals(3L, voteResponse.getNoVotes());
        assertEquals(8L, voteResponse.getTotalVotes());
    }

    @Test
    public void trying_get_topic_results_and_throw_BusinessException() {
        when(topicService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessServiceException.class, ()-> voteService.getTopicResult(1L));
    }

    @Test
    public void testGetTopicResultWithDatabaseException() {
        var topicEntity = TopicEntity.builder().build();
        when(topicService.findById(1L)).thenReturn(Optional.of(topicEntity));
        doThrow(RuntimeException.class).when(voteRepository).countByTopicAndVoteFalse(topicEntity);

        assertThrows(DatabaseException.class, ()-> voteService.getTopicResult(1L));
    }
}
