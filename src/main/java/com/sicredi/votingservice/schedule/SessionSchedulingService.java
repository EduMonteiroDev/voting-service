package com.sicredi.votingservice.schedule;

import com.sicredi.votingservice.message.producer.SessionProducer;
import com.sicredi.votingservice.service.SessionService;
import com.sicredi.votingservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

import static com.sicredi.votingservice.constants.VotingServiceConstants.FINALIZATION_JOB_MESSAGE;
import static com.sicredi.votingservice.constants.VotingServiceConstants.START_JOB_MESSAGE;
import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionSchedulingService {

    private final SessionService sessionService;
    private final VoteService voteService;
    private final SessionProducer sessionProducer;

    @Scheduled(cron = "15 * * * * *")
    public void triggerClosedSessionsMessages(){
        log.info(START_JOB_MESSAGE);
        var offsetDateTime = OffsetDateTime.now();
        var sessions = sessionService.getClosedSessions(offsetDateTime);
        sessions.stream()
                .filter(session -> isNull(session.getPushedMessage()))
                .forEach(session -> {
                    session.setPushedMessage(Boolean.TRUE);
                    var voteResponse = voteService.getTopicResult(session.getTopic().getTopicId());
                    sessionService.updateSession(session);
                    sessionProducer.sendMessage(session.getTopic().getTopicName(), voteResponse);
                });
        log.info(FINALIZATION_JOB_MESSAGE);
    }
}
