package com.example.schoolmanangement.service.impl;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private static final String REDIS_STUDENT_PREFIX = "REDIS_STUDENT_";
    private static final String REDIS_LOGOUT_PREFIX = "REDIS_LOGOUT";
    private static final long TOKEN_TIME_TO_LIVE = 3600;
    private final RedisTemplate<String, String> redisTemplate;

    public void saveStudentAccessToken(final String username, final String accessToken) {
        log.info("Save student access token: {}, {}", username, accessToken);
        this.redisTemplate
                .opsForValue()
                .set(
                        REDIS_STUDENT_PREFIX + username,
                        accessToken,
                        TOKEN_TIME_TO_LIVE,
                        TimeUnit.SECONDS);
    }

    public Optional<String> getStudentAccessToken(final String username) {
        log.info("Get student access token: {}", username);
        String accessToken = this.redisTemplate.opsForValue().get(REDIS_STUDENT_PREFIX + username);
        return Optional.ofNullable(accessToken);
    }

    public void saveLogoutToken(final String username, final String accessToken) {
        log.info("Save logout access token: {}", accessToken);
        this.redisTemplate
                .opsForValue()
                .set(
                REDIS_LOGOUT_PREFIX + username,
                accessToken,
                TOKEN_TIME_TO_LIVE,
                TimeUnit.SECONDS);
    }

    public Optional<String> getLogoutTokens(final String username) {
        log.info("Get logout access tokens");
        String accessToken = this.redisTemplate.opsForValue().get(REDIS_LOGOUT_PREFIX + username);
        return Optional.ofNullable(accessToken);
    }
}
