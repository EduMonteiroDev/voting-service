package com.sicredi.votingservice.message.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionConsumerTest {
    @Mock
    private SessionConsumer sessionConsumer;

    @Test
    public void receive_message_test() {

        sessionConsumer.receive("Test message");

        verify(sessionConsumer).receive("Test message");
    }
}
