package com.example.schoolmanangement.exception.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BaseErrorResponse {
    private long errorCode;
    private String errorMessage;
}
