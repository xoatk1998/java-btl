package com.example.schoolmanangement.controller;


import com.example.schoolmanangement.dto.request.common.CreateCustomerRequest;
import com.example.schoolmanangement.entity.Customer;
import com.example.schoolmanangement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("")
    public List<Customer> get(@RequestParam(required = false) final String phone,
                              @RequestParam(required = false) final String name,
                              @RequestParam(required = false) final String address){
        return customerService.findCustomer(phone, name, address);
    }

    @DeleteMapping("/{id}")
    public String resetPassword(@PathVariable Long id) {
        return customerService.deleteCustomer(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN', 'OPERATOR')")
    public String saveUser(final @RequestBody CreateCustomerRequest user) {
        return customerService.createCustomer(user);
    }
}
