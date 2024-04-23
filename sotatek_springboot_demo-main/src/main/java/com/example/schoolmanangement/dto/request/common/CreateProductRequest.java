package com.example.schoolmanangement.dto.request.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private BigDecimal price;
    private Long quantity;
}
