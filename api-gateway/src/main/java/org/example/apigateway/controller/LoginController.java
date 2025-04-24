package org.example.apigateway.controller;

import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.commonlibrary.dto.LoginRequestDTO;
import org.example.commonlibrary.dto.UserVM;
import org.example.commonlibrary.service.IUserServiceDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    @DubboReference
    private IUserServiceDubbo userServiceDubbo;

    @PostMapping("/login")
    public ResponseEntity<UserVM> login(@RequestBody LoginRequestDTO dto) {
        UserVM result = userServiceDubbo.login(dto);
        return ResponseEntity.ok(result);
    }
}
