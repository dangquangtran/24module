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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/audit-user")
public class AuditController {
    private final IAuditService auditService;

    @GetMapping("/{id}")
    @Operation(summary = "Get user audit history", description = "Retrieve all historical revisions of a user by user ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User history retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found or no audit history available")
    })
    public ApiResponseWrapper<List<User>> getUserHistory(@PathVariable Long id) {
        List<User> userHistory = auditService.getRevisions(id);

        return new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "User history retrieved successfully",
                userHistory
        );
    }
}
