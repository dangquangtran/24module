package org.example.userservice.service;

import org.example.userservice.dto.user.CreateUserDTO;
import org.example.userservice.dto.user.RegisterRequestDTO;
import org.example.userservice.dto.user.UpdateUserDTO;
import org.example.userservice.dto.user.UserVM;

import java.util.List;

public interface IUserService{
    List<UserVM> getAllUsers();
    UserVM getUserById(Long id);
    UserVM createUser(CreateUserDTO dto);
    UserVM updateUser(Long id, UpdateUserDTO dto);
    void deleteUser(Long id);
    UserVM getUserByUsername(String username);
    UserVM registerUser(RegisterRequestDTO dto);
}
