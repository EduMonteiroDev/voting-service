package com.sicredi.votingservice.controller;

import com.sicredi.votingservice.model.request.SessionRequest;
import com.sicredi.votingservice.service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;


import static com.sicredi.votingservice.constants.VotingServiceConstants.OPENING_SESSION_SUCCESSFULLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionControllerTest {
    @InjectMocks
    private SessionController sessionController;
    @Mock
    private SessionService sessionService;

    @Test
    void open_session_with_success() {
        var topicId = 123L;
        var sessionRequest = SessionRequest.builder().sessionEndTime(1L).build();

        var responseEntity = sessionController.openSession(topicId, sessionRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(OPENING_SESSION_SUCCESSFULLY, responseEntity.getBody());

        verify(sessionService).processSessionOpening(topicId, sessionRequest.getSessionEndTime());
    }

}
