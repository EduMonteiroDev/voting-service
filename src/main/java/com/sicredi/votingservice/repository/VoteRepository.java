package com.sicredi.votingservice.repository;

import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.model.entity.VoteEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VoteRepository extends CrudRepository<VoteEntity, Long> {

    Long countByTopicAndVoteTrue(TopicEntity topicEntity);

    Long countByTopicAndVoteFalse(TopicEntity topicEntity);

    boolean existsByDocumentAndTopic(String document, TopicEntity topic);
}
