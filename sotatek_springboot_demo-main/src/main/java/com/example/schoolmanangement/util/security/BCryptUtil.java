package com.example.schoolmanangement.util.security;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BCryptUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NonNull
    public static String encrypt(@NonNull final String input) {
        return encoder.encode(input);
    }

    public static boolean matches(
            @NonNull final String password, @NonNull final String encryptedPassword) {
        return encoder.matches(password, encryptedPassword);
    }
}
