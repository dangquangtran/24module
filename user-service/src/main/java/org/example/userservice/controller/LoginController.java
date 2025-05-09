package org.example.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.userservice.common.response.ApiResponseWrapper;
import org.example.userservice.dto.login.LoginRequestDTO;
import org.example.userservice.service.ILoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

        @RestController
        @RequestMapping("/api/v1/auth")
        @RequiredArgsConstructor
        public class LoginController {
            private final ILoginService loginService;

            @PostMapping("/login")
            @Operation(summary = "Authenticate user", description = "Login user and return JWT token")
            @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Login successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad request - Validation failed")
            })
            public ResponseEntity<ApiResponseWrapper<String>> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
                ApiResponseWrapper<String> response = new ApiResponseWrapper<>(
                        HttpStatus.OK.value(),
                        "Login successfully",
                        loginService.authenticateUser(loginRequest)
                );
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
