package org.example.commonlibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVM implements Serializable {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Role role;
    private Boolean status;
}
