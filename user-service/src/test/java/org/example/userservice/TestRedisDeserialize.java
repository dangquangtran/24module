package org.example.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.userservice.dto.user.UserVM;

public class TestRedisDeserialize {
    public static void main(String[] args) throws Exception {
        String json = "{ \"id\": 1, \"username\": \"string\", \"password\": \"...\", \"email\": \"string\", \"phone\": null, \"createdAt\": \"2025-04-17T17:23:48.834196\", \"updatedAt\": null, \"role\": \"ADMIN\", \"status\": true }";

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        UserVM user = mapper.readValue(json, UserVM.class);
        System.out.println("✅ OK: " + user.getUsername());
    }
}
