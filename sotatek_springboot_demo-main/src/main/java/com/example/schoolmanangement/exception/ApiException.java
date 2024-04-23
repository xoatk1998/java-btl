package com.example.schoolmanangement.exception;

import com.example.schoolmanangement.exception.dto.BaseErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiException extends RuntimeException{
    private BaseErrorResponse baseErrorResponse;
}
