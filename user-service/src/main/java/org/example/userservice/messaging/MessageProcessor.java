package org.example.userservice.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.userservice.dto.user.MyMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class MessageProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    public Function<String, String> processMessage() {
        return message -> {
            try {
                MyMessage myMessage = objectMapper.readValue(message, MyMessage.class);
                System.out.println("Received: " + myMessage);
                return "Processed: " + myMessage;
            } catch (Exception e) {
                e.printStackTrace();
                return "Error processing message";
            }
        };
    }
}
