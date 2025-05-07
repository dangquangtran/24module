package org.example.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final IUserService userService;

    @Operation(summary = "Get all users", description = "Retrieve a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<UserVM>>> getAllUsers() {
        ApiResponseWrapper<List<UserVM>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Users retrieved successfully",
                userService.getAllUsers()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve a single user by ID. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
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

    @Operation(summary = "Create a new user", description = "Create a user with admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
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

    @Operation(summary = "Register new user", description = "Allow a user to register without requiring admin privileges.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully")
    })
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

    @Operation(summary = "Update user", description = "Update a user by ID. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
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

    @Operation(summary = "Delete user", description = "Delete a user by ID. Admin access required.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Access denied")
    })
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