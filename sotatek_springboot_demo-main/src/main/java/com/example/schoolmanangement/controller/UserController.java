package com.example.schoolmanangement.controller;


import com.example.schoolmanangement.dto.request.common.CreateUserRequest;
import com.example.schoolmanangement.dto.request.common.LoginUserRequest;
import com.example.schoolmanangement.entity.User;

import java.util.List;

import com.example.schoolmanangement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody final LoginUserRequest request){
        return userService.login(request);
    }

    @PutMapping("/reset-password")
    public String resetPassword(@RequestBody String username) {
        return userService.resetPassword(username);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveUser(final @RequestBody CreateUserRequest user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.addNewUser(user);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/logout")
    public String logout(){
        return userService.logout();
    }
}
