package com.example.schoolmanangement.controller;


import com.example.schoolmanangement.dto.request.common.CreateCustomerRequest;
import com.example.schoolmanangement.dto.request.common.CreateProductRequest;
import com.example.schoolmanangement.dto.response.ProductResponse;
import com.example.schoolmanangement.entity.Customer;
import com.example.schoolmanangement.service.CustomerService;
import com.example.schoolmanangement.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("")
    public List<ProductResponse> get(@RequestParam(required = false) final BigDecimal price,
                                     @RequestParam(required = false) final String name,
                                     @RequestParam(required = false) final Sort.Direction direction){
        return productService.findProduct(name, price, direction);
    }

    @DeleteMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String resetPassword(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN', 'OPERATOR')")
    public String addProduct(final @RequestBody CreateProductRequest request) {
        return productService.addNewProduct(request);
    }

    @PostMapping("/{id}/update")
    public String updateProduct(@PathVariable Long id, final @RequestBody CreateProductRequest request) {
        return productService.updateProduct(id, request);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateQuantity(@PathVariable Long id, final @RequestParam BigDecimal price) {
        return productService.updatePrice(id, price);
    }

    @GetMapping("/report")
    public List<ProductResponse> getReport(){
        return productService.reportProduct();
    }

    @GetMapping("/revenue")
    public BigDecimal getRevenue(@RequestParam Instant from, @RequestParam Instant to){
        return productService.findRevenue(from, to);
    }

    @GetMapping("/customer")
    public Customer findCustomer(){
        return productService.findGoldCustomer();
    }
}
