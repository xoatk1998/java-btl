package com.example.schoolmanangement.configuration;


import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Value
@ConfigurationProperties("service.secret")
public class ApiKeyProperties {
    private final String apiKey;
    private final String apiSecret;
}
