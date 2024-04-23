package com.example.schoolmanangement.dto.request.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateCustomerRequest {
    private String name;
    private String phone;
    private String address;
}
