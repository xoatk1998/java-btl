package com.example.schoolmanangement.service.impl;

import com.example.schoolmanangement.dto.request.common.CreateCustomerRequest;
import com.example.schoolmanangement.entity.Customer;
import com.example.schoolmanangement.exception.ApiException;
import com.example.schoolmanangement.exception.dto.BaseErrorResponse;
import com.example.schoolmanangement.repository.CustomerRepository;
import com.example.schoolmanangement.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public String createCustomer(CreateCustomerRequest request) {
        validateCreateCustomerRequest(request);

        Customer customer = Customer.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .address(request.getAddress())
                .build();
        customerRepository.save(customer);
        return "Create customer successful";
    }

    @Override
    public String deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ApiException(new BaseErrorResponse(103, "Customer not existed")));

        customerRepository.deleteById(id);
        return "Delete successful";
    }

    @Override
    public List<Customer> findCustomer(String phone, String name, String address) {
        return customerRepository.findCustomer(phone, name, address);
    }

    private void validateCreateCustomerRequest(CreateCustomerRequest request){
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        if(!request.getPhone().matches(pattern))
            throw new ApiException(new BaseErrorResponse(101, "Phone number is invalid"));

        if(customerRepository.existsByPhone(request.getPhone()))
            throw new ApiException(new BaseErrorResponse(102, "Phone number is existed"));
    }
}
