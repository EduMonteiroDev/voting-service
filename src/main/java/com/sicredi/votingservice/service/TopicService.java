package com.sicredi.votingservice.service;

import com.sicredi.votingservice.exception.DatabaseException;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    public void insertNewTopic(String topicName){
        try{
            topicRepository.save(TopicEntity.builder().topicName(topicName).build());
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }

    public Optional<TopicEntity> findById(Long topicId) {
        try{
            return topicRepository.findById(topicId);
        }catch (RuntimeException e){
            throw new DatabaseException();
        }
    }
}
