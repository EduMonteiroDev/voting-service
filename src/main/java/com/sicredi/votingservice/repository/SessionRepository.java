package com.sicredi.votingservice.repository;

import com.sicredi.votingservice.model.entity.SessionEntity;
import com.sicredi.votingservice.model.entity.TopicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByTopic(TopicEntity topicEntity);
    List<SessionEntity> findBySessionIsClosedTrueOrSessionIsClosedIsNull();
    List<SessionEntity> findBySessionIsClosedFalseOrSessionIsClosedIsNull();
}
