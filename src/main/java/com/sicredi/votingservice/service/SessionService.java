package com.sicredi.votingservice.service;

import com.sicredi.votingservice.model.entity.SessionEntity;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;


@Service
@RequiredArgsConstructor
public class SessionService {
    private final TopicService topicService;
    private final SessionRepository sessionRepository;

    public void openSession(Long topicId, OffsetDateTime sessionEndTime) {
        Optional<TopicEntity> topic = topicService.findById(topicId);
        if (topic.isPresent()) {
            var result = nonNull(sessionEndTime)
                    ? sessionEndTime
                    : OffsetDateTime.now().plusMinutes(1);
            var  session = SessionEntity.builder().sessionId(topicId).topic(topic.get()).finalVoting(result).build();
            try {
                sessionRepository.save(session);
            }catch (RuntimeException e){
                throw new IllegalArgumentException("Houve algum problema de conexão com o banco de dados, tente novamente mais tarde...");
            }
        }else {
            throw new IllegalArgumentException("O tópico de votação não existe!");
        }
    }

    public void validateIfSessionExists(TopicEntity topic) {
        try{
            sessionRepository.findByTopic(topic)
                    .filter(sessionEntity -> OffsetDateTime.now().isBefore(topic.getSession().getFinalVoting()))
                    .orElseThrow(() -> new IllegalArgumentException("A sessão não existe ou já expirou."));
        }catch (RuntimeException e){
            throw new IllegalArgumentException("Houve algum problema de conexão com o banco de dados, tente novamente mais tarde...");
        }
    }

    public void closeSessions(OffsetDateTime sessionCloseTime){
        try{
            var sessions = sessionRepository.findBySessionIsClosedFalseOrSessionIsClosedIsNull();
            sessions.stream()
                    .filter(session -> session.getFinalVoting().isBefore(sessionCloseTime))
                    .forEach(session -> {
                        session.setSessionIsClosed(Boolean.TRUE);
                        sessionRepository.save(session);
                    });
        }catch (RuntimeException e){
            throw new IllegalArgumentException("Houve algum problema de conexão com o banco de dados, tente novamente mais tarde...");
        }
    }

    public List<SessionEntity> getClosedSessions(){
        try {
            return sessionRepository.findBySessionIsClosedTrueOrSessionIsClosedIsNull();
        }catch (RuntimeException e){
            throw new IllegalArgumentException("Houve algum problema de conexão com o banco de dados, tente novamente mais tarde...");
        }
    }

    public void updateSession(SessionEntity session){
        try{
            sessionRepository.save(session);
        }catch (RuntimeException e){
            throw new IllegalArgumentException("Houve algum problema de conexão com o banco de dados, tente novamente mais tarde...");
        }
    }
}
