package com.example.schoolmanangement.service;

import com.example.schoolmanangement.dto.request.common.CreateCustomerRequest;
import com.example.schoolmanangement.entity.Customer;

import java.util.List;

public interface CustomerService {
    String createCustomer(CreateCustomerRequest createCustomerRequest);

    String deleteCustomer(Long id);

    List<Customer> findCustomer(String phone, String name, String address);
}
