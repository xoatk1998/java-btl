package com.example.schoolmanangement.dto.request.common;

import com.example.schoolmanangement.dto.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateUserRequest {
    private String username;
    private String password;
    private String name;
    private String phone;
    private String email;
    private Role role;
}
