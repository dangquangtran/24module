package org.example.userservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 5, max = 30, message = "Password must be between 5 and 30 characters")
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 30, message = "Full name must be between 2 and 30 characters")
    private String fullname;
}
