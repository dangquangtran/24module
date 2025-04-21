package org.example.userservice.service;

import org.example.userservice.dto.login.LoginRequestDTO;

public interface ILoginService {
    String authenticateUser(LoginRequestDTO loginRequest);
}
