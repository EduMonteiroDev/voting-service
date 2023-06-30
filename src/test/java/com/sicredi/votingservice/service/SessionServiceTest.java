package com.sicredi.votingservice.service;

import com.sicredi.votingservice.exception.BusinessServiceException;
import com.sicredi.votingservice.exception.DatabaseException;
import com.sicredi.votingservice.model.entity.SessionEntity;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.repository.SessionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {
    @InjectMocks
    private SessionService sessionService;
    @Mock
    private TopicService topicService;
    @Mock
    private SessionRepository sessionRepository;

    @ParameterizedTest
    @MethodSource("openSessionsParams")
    public void process_open_session_with_success(Long input1, Long input2) {
        var topicEntity = TopicEntity.builder().build();

        when(topicService.findById(input1)).thenReturn(Optional.of(topicEntity));

        sessionService.processSessionOpening(input1, input2);

        verify(sessionRepository, times(1)).save(any(SessionEntity.class));
    }
    @Test
    public void process_open_session_and_throw_BusinessServiceException() {
        when(topicService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessServiceException.class, ()->sessionService.processSessionOpening(1L, 1L));

        verify(sessionRepository, never()).save(any(SessionEntity.class));
    }

    @Test
    public void process_open_session_and_throw_DatabaseException() {
        var topicEntity = TopicEntity.builder().build();

        when(topicService.findById(1L)).thenReturn(Optional.of(topicEntity));
        doThrow(RuntimeException.class).when(sessionRepository).save(any());

        assertThrows(DatabaseException.class, ()->sessionService.processSessionOpening(1L, 1L));
    }

    @Test
    public void validate_session_existence_with_success() {
        var sessionEntity = SessionEntity.builder().sessionEndTime(OffsetDateTime.now().plusMinutes(1)).build();
        var topicEntity = TopicEntity.builder().session(sessionEntity).build();

        when(sessionRepository.findByTopic(topicEntity)).thenReturn(Optional.of(sessionEntity));

        sessionService.validateIfSessionExists(topicEntity);
    }

    @Test
    public void validate_session_existence_and_throw_BusinessException() {
        var sessionEntity = SessionEntity.builder().sessionEndTime(OffsetDateTime.now()).build();
        var topicEntity = TopicEntity.builder().session(sessionEntity).build();

        when(sessionRepository.findByTopic(topicEntity)).thenReturn(Optional.of(sessionEntity));

        assertThrows(BusinessServiceException.class, ()-> sessionService.validateIfSessionExists(topicEntity));
    }

    @Test
    public void get_closed_sessions_with_success() {
        var sessionEndTime = OffsetDateTime.now();
        var expectedSessions = List.of(SessionEntity.builder().build(), SessionEntity.builder().build());
        when(sessionRepository.findBySessionEndTimeBefore(sessionEndTime)).thenReturn(expectedSessions);

        var actualSessions = sessionService.getClosedSessions(sessionEndTime);

        assertEquals(expectedSessions, actualSessions);
    }

    @Test
    public void trying_get_closed_sessions_and_throw_DatabaseException() {
        var sessionEndTime = OffsetDateTime.now();

        doThrow(RuntimeException.class).when(sessionRepository).findBySessionEndTimeBefore(sessionEndTime);

        assertThrows(DatabaseException.class, ()-> sessionService.getClosedSessions(sessionEndTime));
    }

    @Test
    public void update_session_with_success() {
        var sessionEntity = SessionEntity.builder().build();

        sessionService.updateSession(sessionEntity);

        verify(sessionRepository, times(1)).save(sessionEntity);
    }

    @Test
    public void trying_update_session_and_throw_DatabaseException() {
        var sessionEntity = SessionEntity.builder().build();

        doThrow(RuntimeException.class).when(sessionRepository).save(sessionEntity);
        assertThrows(DatabaseException.class, ()-> sessionService.updateSession(sessionEntity));
    }

    @Test
    public void find_session_by_topic_with_success() {
        var topicEntity = TopicEntity.builder().build();
        var expectedSession = Optional.of(SessionEntity.builder().build());
        when(sessionRepository.findByTopic(topicEntity)).thenReturn(expectedSession);

        var actualSession = sessionService.findSessionByTopic(topicEntity);

        assertEquals(expectedSession, actualSession);
    }

    @Test
    public void trying_find_session_by_topic_and_throw_DatabaseException() {
        var topicEntity = TopicEntity.builder().build();
        doThrow(RuntimeException.class).when(sessionRepository).findByTopic(topicEntity);

        assertThrows(DatabaseException.class, ()-> sessionService.findSessionByTopic(topicEntity));
    }

    static Stream<Arguments> openSessionsParams() {
        return Stream.of(
                Arguments.of(1L, 1L),
                Arguments.of(1L, null)
        );
    }

}
