package com.example.schoolmanangement.service;

import com.example.schoolmanangement.dto.request.common.CreateUserRequest;
import com.example.schoolmanangement.dto.request.common.LoginUserRequest;

public interface UserService {
    String login(LoginUserRequest loginUserRequest);

    String resetPassword(String username);

    String addNewUser(CreateUserRequest request);

    String logout();
}
