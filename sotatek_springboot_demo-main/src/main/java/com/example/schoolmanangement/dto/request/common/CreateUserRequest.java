package com.example.schoolmanangement.dto.request.common;

import com.example.schoolmanangement.dto.Role;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class CreateUserRequest {
    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Role role;
}
