package com.sicredi.votingservice.service;

import com.sicredi.votingservice.exception.BusinessServiceException;
import com.sicredi.votingservice.exception.DatabaseException;
import com.sicredi.votingservice.model.entity.SessionEntity;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static com.sicredi.votingservice.constants.VotingServiceConstants.SESSION_EXIST_EXCEPTION;
import static com.sicredi.votingservice.constants.VotingServiceConstants.TOPIC_EXIST_EXCEPTION;
import static java.util.Objects.nonNull;


@Service
@RequiredArgsConstructor
public class SessionService {
    private final TopicService topicService;
    private final SessionRepository sessionRepository;

    public void processSessionOpening(Long topicId, Long minutes) {
        var topic = topicService.findById(topicId)
                .orElseThrow(() -> new BusinessServiceException(TOPIC_EXIST_EXCEPTION));
        var sessionEndTime = nonNull(minutes) ? OffsetDateTime.now().plus(Duration.ofMinutes(minutes)) : OffsetDateTime.now().plusMinutes(1);
        var session = SessionEntity.builder()
                .sessionId(topicId)
                .topic(topic)
                .sessionEndTime(sessionEndTime)
                .build();
        try {
            sessionRepository.save(session);
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }

    public void validateIfSessionExists(TopicEntity topic) {
        findSessionByTopic(topic)
                .filter(sessionEntity -> OffsetDateTime.now().isBefore(topic.getSession().getSessionEndTime()))
                .orElseThrow(() -> new BusinessServiceException(SESSION_EXIST_EXCEPTION));
    }

    public List<SessionEntity> getClosedSessions(OffsetDateTime sessionEndTime){
        try {
            return sessionRepository.findBySessionEndTimeBefore(sessionEndTime);
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }

    public void updateSession(SessionEntity session){
        try{
            sessionRepository.save(session);
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }

    public Optional<SessionEntity> findSessionByTopic(TopicEntity topic){
        try{
            return sessionRepository.findByTopic(topic);
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }
}
