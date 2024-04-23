package com.example.schoolmanangement.dto.response.test;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component("person")
public class Person {
    private String name;
    private List<String> address;

    public Person() {
        this.name = "David";
        this.address = List.of("Phu Yen");
    }
}
