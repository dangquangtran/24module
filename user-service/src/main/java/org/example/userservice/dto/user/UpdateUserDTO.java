package org.example.userservice.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.example.userservice.entity.Role;

@Data
public class UpdateUserDTO {
    @NotBlank(message = "Username cannot be blank")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30 characters")
    private String fullname;

    private Role role;
    private Boolean status;
}
