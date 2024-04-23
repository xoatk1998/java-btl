package com.example.schoolmanangement.service;

import com.example.schoolmanangement.dto.request.common.CreateOrderRequest;

public interface OrderService {
    String createOrder(CreateOrderRequest request);
}
