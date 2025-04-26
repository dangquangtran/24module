package org.example.userservice.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class MessageProducer {

    @Bean
    public Supplier<String> outputChannel() {
        return () -> "Tin nhắn từ Producer!";
    }
}
