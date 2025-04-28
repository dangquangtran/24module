package org.example.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.common.response.ApiResponseWrapper;
import org.example.userservice.dto.user.CreateUserDTO;
import org.example.userservice.dto.user.RegisterRequestDTO;
import org.example.userservice.dto.user.UpdateUserDTO;
import org.example.userservice.dto.user.UserVM;
import org.example.userservice.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor()
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<UserVM>>> getAllUsers() {
        ApiResponseWrapper<List<UserVM>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User retrieved successfully",
                userService.getAllUsers()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<UserVM>> getUserById(@PathVariable Long id) {
        UserVM user = userService.getUserById(id);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User retrieved successfully",
                user
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<UserVM>> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        UserVM created = userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseWrapper<>(
                        HttpStatus.CREATED.value(),
                        "User created successfully",
                        created
                ));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseWrapper<UserVM>> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        UserVM created = userService.registerUser(registerRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseWrapper<>(
                        HttpStatus.CREATED.value(),
                        "Register successfully",
                        created
                ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<UserVM>> updateUser(@Valid @PathVariable Long id, @RequestBody UpdateUserDTO dto) {
        UserVM updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User updated successfully",
                updated
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseWrapper<String>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User deleted successfully",
                null
        ));
    }

}
