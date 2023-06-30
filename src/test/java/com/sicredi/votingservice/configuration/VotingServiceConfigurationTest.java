package com.sicredi.votingservice.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VotingServiceConfigurationTest {
    @InjectMocks
    private VotingServiceConfiguration votingServiceConfiguration;
    @Mock
    private Environment environment;

    @Test
    public void get_env_for_queue_name_with_success() {
        var queueName = "testQueue";
        when(environment.getProperty("spring.rabbitmq.queue-name")).thenReturn("testQueue");

        var result = votingServiceConfiguration.getQueueName();

        assertEquals(queueName, result);
    }
}