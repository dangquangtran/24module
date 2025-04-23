package org.example.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.userservice.dto.user.CreateUserDTO;
import org.example.userservice.dto.user.UpdateUserDTO;
import org.example.userservice.dto.user.UserVM;
import org.example.userservice.entity.Role;
import org.example.userservice.entity.User;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor()
@Service
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserVM> getAllUsers() {
        System.out.println(passwordEncoder.encode("string"));

        return userMapper.toVMList(userRepository.findAll());
    }

    @Cacheable(value = "users", key = "#id")
    public UserVM getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return userMapper.toUserVM(user);
    }

    public UserVM createUser(CreateUserDTO dto) {
        User user = userMapper.toUser(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        user.setRole(Role.ADMIN);
        user.setStatus(true);
        user.setCreatedAt(LocalDateTime.now());
        User saved = userRepository.save(user);
        return userMapper.toUserVM(saved);
    }

    @CachePut(value = "users", key = "#id")
    public UserVM updateUser(Long id, UpdateUserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        userMapper.updateUserFromDTO(dto, user);
        user.setUpdatedAt(LocalDateTime.now());
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
