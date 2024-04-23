package com.example.schoolmanangement.configuration.redis;


import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
public class KeyConfiguration {

    @Bean
    public static CustomKeyGenerator customKeyGenerator() {
        return new CustomKeyGenerator();
    }

    public static class CustomKeyGenerator implements KeyGenerator {
        public Object generate(Object target, Method method, Object... params) {
            return target.getClass().getSimpleName()
                    + "_"
                    + method.getName()
                    + "_"
                    + StringUtils.arrayToDelimitedString(params, "_");
        }
    }
}
