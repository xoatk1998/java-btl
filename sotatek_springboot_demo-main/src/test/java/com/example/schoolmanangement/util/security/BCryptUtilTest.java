package com.example.schoolmanangement.util.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BCryptUtilTest {

    @Test
    void shouldEncryptText() {
        final String text = "hahaha";
        final String encryptedText = BCryptUtil.encrypt(text);
        Assertions.assertNotNull(encryptedText);
    }

    @Test
    void shouldMatchText() {
        final String text = "jason01";
        final String text2 = "jason02";
        final String encryptedText = BCryptUtil.encrypt(text);

        assertTrue(BCryptUtil.matches(text, encryptedText));
        assertFalse(BCryptUtil.matches(text2, encryptedText));
    }
}
