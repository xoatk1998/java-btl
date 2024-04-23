package com.example.schoolmanangement.security.filter;


import com.example.schoolmanangement.exception.ApiException;
import com.example.schoolmanangement.exception.dto.BaseErrorResponse;
import com.example.schoolmanangement.service.impl.RedisService;
import com.example.schoolmanangement.util.jwt.JwtUserPayload;
import com.example.schoolmanangement.util.jwt.JwtUtil;
import com.example.schoolmanangement.util.jwt.error.InvalidJwtError;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final RedisService redisService;

    private final AuthenticationEntryPoint authenticationEntryPoint;

    private final PublicKey publicKey;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!this.isBearerToken(authHeader)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwtToken = this.extractJwtFromAuthorizationHeader(authHeader);
        final Authentication authentication;

        try {
            authentication = this.getAuthenticationByJwt(jwtToken, this.publicKey);
            Optional<String> logoutToken = redisService.getLogoutTokens((String) authentication.getPrincipal());
           if(logoutToken.isPresent() && jwtToken.equals(logoutToken.get())){
               throw new ApiException(new BaseErrorResponse(1000, "User is already logout"));
           }
        } catch (InvalidJwtError error) {
            SecurityContextHolder.clearContext();
            this.authenticationEntryPoint.commence(request, response, new InvalidJwtError());
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @NonNull
    private Authentication getAuthenticationByJwt(
            @NonNull final String jwtToken, @NonNull final PublicKey publicKey) {
        final Claims claims = JwtUtil.extractAllClaims(jwtToken, publicKey);
        final JwtUserPayload jwtUserPayload = JwtUserPayload.from(claims);
        final String username = jwtUserPayload.getUsername();
        final String role = jwtUserPayload.getRole();
        final List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_"+role));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private boolean isBearerToken(@Nullable final String authHeader) {
        return StringUtils.isNotEmpty(authHeader)
                && StringUtils.startsWithIgnoreCase(authHeader, BEARER_PREFIX);
    }

    @NonNull
    private String extractJwtFromAuthorizationHeader(@NonNull final String authorizationHeader) {
        if (authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length()).trim();
        }
        throw new InvalidJwtError();
    }
}
