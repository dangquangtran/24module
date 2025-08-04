package org.example.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MyMessage {
    private String content;
    private String sender;

    // Getters and setters

    @Override
    public String toString() {
        return "MyMessage{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}

