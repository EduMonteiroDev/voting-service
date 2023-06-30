package com.sicredi.votingservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {

    private final VotingServiceConfiguration votingServiceConfiguration;

    @Bean
    public Queue votingQueue() {
        return new Queue(votingServiceConfiguration.getQueueName(), true);
    }
}
