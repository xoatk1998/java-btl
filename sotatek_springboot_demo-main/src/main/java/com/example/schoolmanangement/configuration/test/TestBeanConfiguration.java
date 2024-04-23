package com.example.schoolmanangement.configuration.test;


import com.example.schoolmanangement.dto.response.test.Person;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestBeanConfiguration {

    @Bean("person01")
    public Person getDefaultPerson() {
        return new Person("Jason", List.of("Ha Noi 2"));
    }

    @Bean("person02")
    public Person getDefaultPersonV2() {
        return new Person("Peter", List.of("Ha Noi"));
    }
}
