package com.example.schoolmanangement.util.jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtPayload {
    private final String iss;
    private final String sub;
    private final long iat;
    private final long exp;
}
