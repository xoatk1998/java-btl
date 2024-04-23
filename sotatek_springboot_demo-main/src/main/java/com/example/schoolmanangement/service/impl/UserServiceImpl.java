package com.example.schoolmanangement.service.impl;

import com.example.schoolmanangement.dto.request.common.CreateUserRequest;
import com.example.schoolmanangement.dto.request.common.LoginUserRequest;
import com.example.schoolmanangement.entity.User;
import com.example.schoolmanangement.exception.ApiException;
import com.example.schoolmanangement.exception.dto.BaseErrorResponse;
import com.example.schoolmanangement.repository.UserRepository;
import com.example.schoolmanangement.service.UserService;
import com.example.schoolmanangement.util.jwt.JwtUtil;
import com.example.schoolmanangement.util.security.BCryptUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RedisService redisService;

    private final PrivateKey privateKey;

    private final HttpServletRequest request;

    @Override
    public String login(LoginUserRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ApiException(new BaseErrorResponse(106, "Wrong username")));

        if (!BCryptUtil.matches(request.getPassword(), request.getPassword())) {
            throw new ApiException(new BaseErrorResponse(105, "Wrong password"));
        }

        Optional<String> accessToken = redisService.getStudentAccessToken(request.getUsername());
        if (accessToken.isPresent()) {
            return accessToken.get();
        }

        final ZonedDateTime issuedAt = ZonedDateTime.now();
        final ZonedDateTime expiredAt = issuedAt.plusHours(2);

        String token =
                JwtUtil.generateAccessToken(
                        JwtUtil.RS256_TOKEN_HEADER,
                        "storemanagement",
                        this.generateClaims(user),
                        "student_access_token",
                        issuedAt,
                        expiredAt,
                        privateKey);

        redisService.saveStudentAccessToken(request.getUsername(), token);
        return token;
    }

    @Override
    public String resetPassword(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->new ApiException(new BaseErrorResponse(104, "User not existed")));
        String newPassword = UUID.randomUUID().toString();
        user.setPassword(BCryptUtil.encrypt(newPassword));
        userRepository.save(user);
        return String.format("Your new password : %s", newPassword);
    }

    @Override
    public String addNewUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(BCryptUtil.encrypt(request.getPassword()))
                .name(request.getName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        userRepository.save(user);
        return "Create successful";
    }

    @Override
    public String logout() {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authHeader.substring(7).trim();

        UsernamePasswordAuthenticationToken currentUser = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String username = (String) currentUser.getPrincipal();
        redisService.saveLogoutToken(username, token);
        return "Logout successful";
    }

    private Claims generateClaims(User student) {
        final Claims claims = Jwts.claims();
        claims.put("id", student.getId());
        claims.put("username", student.getUsername());
        claims.put("role", student.getRole());

        return claims;
    }
}
