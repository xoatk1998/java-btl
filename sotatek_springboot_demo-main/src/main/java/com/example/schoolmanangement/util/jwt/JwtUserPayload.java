package com.example.schoolmanangement.util.jwt;


import io.jsonwebtoken.Claims;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class JwtUserPayload extends JwtPayload {
    private final String username;
    private final String role;

    @Builder
    public JwtUserPayload(
            final String iss,
            final String sub,
            final long iat,
            final long exp,
            final String username,
            final String role) {
        super(iss, sub, iat, exp);
        this.username = username;
        this.role = role;
    }

    @NonNull
    public static JwtUserPayload from(@NonNull final Claims claims) {
        return JwtUserPayload.builder()
                .iss(claims.getIssuer())
                .exp(claims.getExpiration().getTime())
                .iat(claims.getIssuedAt().getTime())
                .sub(claims.getSubject())
                .username(claims.get("username", String.class))
                .role(claims.get("role", String.class))
                .build();
    }
}
