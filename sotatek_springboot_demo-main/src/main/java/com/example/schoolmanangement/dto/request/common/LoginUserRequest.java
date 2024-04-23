package com.example.schoolmanangement.dto.request.common;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginUserRequest {
    private String username;
    private String password;
}
