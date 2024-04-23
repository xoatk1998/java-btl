package com.example.schoolmanangement.controller.test;


import com.example.schoolmanangement.dto.response.test.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBeanController {

    @Qualifier("person")
    @Autowired
    private Person person;

    @Qualifier("person01")
    @Autowired
    private Person person1;

    @Qualifier("person02")
    @Autowired
    private Person person2;

    @GetMapping("/v0/test/get-user-info")
    public Person getPerson() {
        return person;
    }

    @GetMapping("/v1/test/get-user-info")
    public Person getPersonV1() {
        return person1;
    }

    @GetMapping("/v2/test/get-user-info")
    public Person getPersonV2() {
        return person2;
    }
}
