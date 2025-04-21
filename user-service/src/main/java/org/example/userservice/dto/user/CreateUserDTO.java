package org.example.userservice.dto.user;

import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String password;
    private String email;
    private String fullname;
}
