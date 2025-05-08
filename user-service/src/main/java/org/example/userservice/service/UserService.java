package org.example.userservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dto.user.*;
import org.example.userservice.entity.Role;
import org.example.userservice.entity.User;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor()
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate;
    private final StreamBridge streamBridge;

    public List<UserVM> getAllUsers() {
        return userMapper.toVMList(userRepository.findAll());
    }

    @Cacheable(value = "users", key = "#id")
    public UserVM getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toUserVM(user);
    }

    public UserVM registerUser(RegisterRequestDTO dto) {
        if (userRepository.existsByUsername((dto.getUsername()))) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail((dto.getEmail()))) {
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper.toUser(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setStatus(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.CUSTOMER);
        User saved = userRepository.save(user);
        sendDiscordNotification(dto.getUsername(), LocalDateTime.now().toString());
        try {
        MyMessage message = new MyMessage(dto.getUsername(), "User registered at: " + LocalDateTime.now());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonMessage = objectMapper.writeValueAsString(message);
        streamBridge.send("outputChannel-out-0", jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return userMapper.toUserVM(saved);
    }

    public UserVM createUser(CreateUserDTO dto) {
        if (userRepository.existsByUsername((dto.getUsername()))) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail((dto.getEmail()))) {
            throw new RuntimeException("Email already exists");
        }
        User user = userMapper.toUser(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setStatus(true);
        User saved = userRepository.save(user);
        sendDiscordNotification(dto.getUsername(), LocalDateTime.now().toString());
        return userMapper.toUserVM(saved);
    }
    private void sendDiscordNotification(String username, String createdAt) {
        // URL API Discord Webhook
        String discordWebhookUrl = "https://discord.com/api/webhooks/1365172762775261204/zl1JZBNsEheG1pqPBKRxLIVpL_tpCX0FMVqpXxIwpIm2Rrt-EoWeF8MSlYEj23oolIW1";

        // Tạo body cho yêu cầu
        String jsonBody = "{\n" +
                "  \"content\": null,\n" +
                "  \"embeds\": [\n" +
                "    {\n" +
                "      \"title\": \"New user\",\n" +
                "      \"color\": null,\n" +
                "      \"fields\": [\n" +
                "        {\n" +
                "          \"name\": \"username\",\n" +
                "          \"value\": \"" + username + "\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"created_at\",\n" +
                "          \"value\": \"" + createdAt + "\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"username\": \"Vipbot\",\n" +
                "  \"attachments\": []\n" +
                "}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        restTemplate.exchange(discordWebhookUrl, HttpMethod.POST, entity, String.class);
    }

    @CachePut(value = "users", key = "#id")
    public UserVM updateUser(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.updateUserFromDTO(dto, user);
        User updated = userRepository.save(user);
        return userMapper.toUserVM(updated);
    }

    @CacheEvict(value = "users", key = "#id")
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userRepository.delete(user);
    }

    public UserVM getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return userMapper.toUserVM(user);
    }



}
