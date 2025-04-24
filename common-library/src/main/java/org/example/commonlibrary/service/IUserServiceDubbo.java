package org.example.commonlibrary.service;

import org.example.commonlibrary.dto.LoginRequestDTO;
import org.example.commonlibrary.dto.UserVM;

public interface IUserServiceDubbo {
    UserVM login(LoginRequestDTO loginRequestDTO);
}
