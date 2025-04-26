package org.example.userservice.messaging;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class MessageConsumer {

    @Bean
    public Consumer<String> inputChannel() {
        return message -> System.out.println("Nhận tin nhắn: " + message);
    }
}
