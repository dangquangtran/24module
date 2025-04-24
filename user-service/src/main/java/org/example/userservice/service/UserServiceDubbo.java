package org.example.userservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.commonlibrary.dto.LoginRequestDTO;
import org.example.commonlibrary.dto.UserVM;
import org.example.commonlibrary.service.IUserServiceDubbo;
import org.example.userservice.entity.User;
import org.example.userservice.mapper.UserMapper;
import org.example.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@DubboService
public class UserServiceDubbo  implements IUserServiceDubbo {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserVM login(LoginRequestDTO loginRequestDTO) {
        User user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + loginRequestDTO.getUsername()));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return userMapper.toUserVMDubbo(user);
    }
}
