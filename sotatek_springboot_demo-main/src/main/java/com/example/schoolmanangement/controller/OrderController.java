package com.example.schoolmanangement.controller;


import com.example.schoolmanangement.dto.request.common.CreateCustomerRequest;
import com.example.schoolmanangement.dto.request.common.CreateOrderRequest;
import com.example.schoolmanangement.entity.Customer;
import com.example.schoolmanangement.service.CustomerService;
import com.example.schoolmanangement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
    public String saveUser(final @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
