package com.sicredi.votingservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;


@Configuration
@RequiredArgsConstructor
public class VotingServiceConfiguration {

    private final Environment environment;

    public String getQueueName(){
        return environment.getProperty("spring.rabbitmq.queue-name");
    }
}
