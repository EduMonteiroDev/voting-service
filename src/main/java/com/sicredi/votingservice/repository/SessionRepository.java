package com.sicredi.votingservice.repository;

import com.sicredi.votingservice.model.entity.SessionEntity;
import com.sicredi.votingservice.model.entity.TopicEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends CrudRepository<SessionEntity, Long> {

    Optional<SessionEntity> findByTopic(TopicEntity topicEntity);
    @Query("SELECT s FROM SessionEntity s WHERE s.sessionEndTime < :endTime")
    List<SessionEntity> findBySessionEndTimeBefore(OffsetDateTime endTime);
}
