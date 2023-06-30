package com.sicredi.votingservice.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RabbitMQConfigurationTest {
    @InjectMocks
    private RabbitMQConfiguration rabbitMQConfiguration;
    @Mock
    private VotingServiceConfiguration votingServiceConfiguration;

    @Test
    public void get_queue_with_success() {
        when(votingServiceConfiguration.getQueueName()).thenReturn("testQueue");

        var queue = rabbitMQConfiguration.votingQueue();

        assertEquals("testQueue", queue.getName());
        assertEquals(true, queue.isDurable());
    }
}
