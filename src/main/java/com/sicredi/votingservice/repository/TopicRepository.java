package com.sicredi.votingservice.repository;

import com.sicredi.votingservice.model.entity.TopicEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<TopicEntity, Long> {
}
