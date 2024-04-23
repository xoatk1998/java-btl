package com.example.schoolmanangement.service;

import com.example.schoolmanangement.dto.request.common.CreateProductRequest;
import com.example.schoolmanangement.dto.response.ProductResponse;
import com.example.schoolmanangement.entity.Customer;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface ProductService {
    String addNewProduct(CreateProductRequest request);

    List<ProductResponse> findProduct(String name, BigDecimal price, Sort.Direction direction);

    String updateProduct(Long id, CreateProductRequest request);

    String deleteProduct(Long id);

    String updatePrice(Long id, BigDecimal price);

    List<ProductResponse> reportProduct();

    BigDecimal findRevenue(Instant from, Instant to);

    Customer findGoldCustomer();
}
