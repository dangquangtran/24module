package org.example.userservice.controller;


import lombok.AllArgsConstructor;
import org.example.userservice.common.response.ApiResponseWrapper;
import org.example.userservice.entity.User;
import org.example.userservice.service.IAuditService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/audit-user")
public class AuditController {
    private final IAuditService auditService;

    @GetMapping("/{id}")
    public ApiResponseWrapper<List<User>> getUserHistory(@PathVariable Long id) {
        List<User> userHistory = auditService.getRevisions(id);

        return new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User history retrieved successfully",
                userHistory
        );
    }
}
