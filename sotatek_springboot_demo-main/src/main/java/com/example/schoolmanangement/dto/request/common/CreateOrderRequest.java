package com.example.schoolmanangement.dto.request.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CreateOrderRequest {
    private Long customerId;
    private List<ProductInOrder> productsOrder;
}
