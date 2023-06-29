package com.sicredi.votingservice.schedule;

import com.sicredi.votingservice.message.producer.SessionProducer;
import com.sicredi.votingservice.service.SessionService;
import com.sicredi.votingservice.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class SessionSchedulingService {

    private final SessionService sessionService;
    private final VoteService voteService;
    private final SessionProducer sessionProducer;

    @Scheduled(cron = "15 * * * * *")
    public void closeSession(){
        log.info("Close sessions in process...");
        var sessionCloseTime = OffsetDateTime.now();
        sessionService.closeSessions(sessionCloseTime);
        log.info("Sessions until "+sessionCloseTime+" is closed.");
    }

    @Scheduled(cron = "15 * * * * *")
    public void triggerClosedSessionsMessages(){
        log.info("Preparing to inform the platform of the poll results...");
        var sessions = sessionService.getClosedSessions();
        sessions.stream()
                .filter(session -> isNull(session.getResultSendMessage()))
                .forEach(session -> {
                    session.setResultSendMessage(Boolean.TRUE);
                    var yesVotes = voteService.getAllYesVotes(session.getTopic());
                    var noVotes = voteService.getAllNoVotes(session.getTopic());
                    sessionService.updateSession(session);
                    sessionProducer.sendMessage(session.getTopic().getTopicName(), yesVotes, noVotes);
                });
        log.info("The results have been pushed!");
    }
}
