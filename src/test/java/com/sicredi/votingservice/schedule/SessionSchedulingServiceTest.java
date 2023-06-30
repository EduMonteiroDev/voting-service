package com.sicredi.votingservice.schedule;

import com.sicredi.votingservice.message.producer.SessionProducer;
import com.sicredi.votingservice.model.entity.SessionEntity;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.model.response.VoteResponse;
import com.sicredi.votingservice.service.SessionService;
import com.sicredi.votingservice.service.VoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionSchedulingServiceTest {
    @InjectMocks
    private SessionSchedulingService sessionSchedulingService;
    @Mock
    private SessionService sessionService;
    @Mock
    private VoteService voteService;
    @Mock
    private SessionProducer sessionProducer;

    @Test
    void trigger_closed_sessions_and_send_messages_with_success() {
        var topic = TopicEntity.builder().topicId(1L).topicName("TEST01").build();
        var session1 = SessionEntity.builder().sessionId(1L).topic(topic).pushedMessage(null).build();
        var session2 = SessionEntity.builder().sessionId(2L).topic(topic).pushedMessage(null).build();

        when(sessionService.getClosedSessions(any())).thenReturn(List.of(session1,session2));
        when(voteService.getTopicResult(1L)).thenReturn(VoteResponse.builder().build());

        sessionSchedulingService.triggerClosedSessionsMessages();

        verify(sessionService, times(2)).updateSession(any(SessionEntity.class));
        verify(sessionProducer, times(2)).sendMessage(anyString(), any(VoteResponse.class));
    }
}
