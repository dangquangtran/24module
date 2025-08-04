package org.example.productservice.messaging;

import org.example.productservice.dto.MyMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Consumer;

@Service
public class MessageConsumer {

    @Bean
    public Consumer<MyMessage> inputChannel() {
        return message -> {
            System.out.println("🔥 Nhận tin nhắn: " + message);
        };
    }}
