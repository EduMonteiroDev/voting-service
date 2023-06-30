package com.sicredi.votingservice.service;

import com.sicredi.votingservice.exception.DatabaseException;
import com.sicredi.votingservice.model.entity.TopicEntity;
import com.sicredi.votingservice.repository.TopicRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {
    @InjectMocks
    private TopicService topicService;
    @Mock
    private TopicRepository topicRepository;

    @Test
    public void insert_new_topic_with_success() {
        var topicEntity = TopicEntity.builder().topicName("TEST").build();

        topicService.insertNewTopic("TEST");

        verify(topicRepository, times(1)).save(topicEntity);
    }

    @Test
    public void trying_insert_new_topic_and_throw_DatabaseException() {
        var topicEntity = TopicEntity.builder().topicName("TEST").build();

        doThrow(RuntimeException.class).when(topicRepository).save(topicEntity);

        assertThrows(DatabaseException.class, ()-> topicService.insertNewTopic("TEST"));
    }

    @Test
    public void find_by_topicId_with_success() {
        var expectedTopic = TopicEntity.builder().build();
        when(topicRepository.findById(1L)).thenReturn(Optional.of(expectedTopic));

        var actualTopic = topicService.findById(1L);

        assertEquals(Optional.of(expectedTopic), actualTopic);
    }
    @Test
    public void trying_find_by_topicId_and_throw_DatabaseException() {
        doThrow(RuntimeException.class).when(topicRepository).findById(1L);

        assertThrows(DatabaseException.class,()-> topicService.findById(1L));
    }
}
