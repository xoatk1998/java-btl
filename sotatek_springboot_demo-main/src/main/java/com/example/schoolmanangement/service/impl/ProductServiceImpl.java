package com.example.schoolmanangement.service.impl;

import com.example.schoolmanangement.dto.request.common.CreateProductRequest;
import com.example.schoolmanangement.dto.response.ProductResponse;
import com.example.schoolmanangement.entity.Customer;
import com.example.schoolmanangement.entity.Inventory;
import com.example.schoolmanangement.entity.LineOrder;
import com.example.schoolmanangement.entity.Product;
import com.example.schoolmanangement.exception.ApiException;
import com.example.schoolmanangement.exception.dto.BaseErrorResponse;
import com.example.schoolmanangement.repository.*;
import com.example.schoolmanangement.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final InventoryRepository inventoryRepository;

    private final OrderRepository orderRepository;

    private final LineOrderRepository lineOrderRepository;

    private final CustomerRepository customerRepository;
    @Override
    public String addNewProduct(CreateProductRequest request) {
        Product product = productRepository.save(Product.builder()
                        .name(request.getName())
                        .price(request.getPrice())
                .build());

        inventoryRepository.save(Inventory.builder()
                        .productId(product.getId())
                        .updatedDate(Instant.now())
                        .stockQuantity(request.getQuantity())
                .build());
        return "Create successful";
    }

    @Override
    public List<ProductResponse> findProduct(String name, BigDecimal price, Sort.Direction direction) {
        Sort sort = Sort.by(direction, "price");
        return productRepository.findProductByPriceAndName(price, name, sort);
    }

    @Override
    public String updateProduct(Long id, CreateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ApiException(new BaseErrorResponse(107, "Product is not existed")));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productRepository.save(product);
        return "Update successful";
    }

    @Override
    public String deleteProduct(Long id) {
        //Normal : soft delete
        productRepository.deleteById(id);
        return "Delete successful";
    }

    @Override
    public String updateQuantity(Long id, Long quantity) {
    Inventory inventory = inventoryRepository.findByProductId(id).orElseThrow(() -> new ApiException(new BaseErrorResponse(107, "Product is not existed")));;
        inventory.setStockQuantity(quantity);
        inventoryRepository.save(inventory);
        return "Update quantity successful";
    }

    @Override
    public List<ProductResponse> reportProduct() {
        return productRepository.findProductReport();
    }

    @Override
    public BigDecimal findRevenue(Instant from, Instant to) {
        return orderRepository.findRevenue(from, to);
    }

    @Override
    public Customer findGoldCustomer() {
        Long customerId = lineOrderRepository.findGoldCustomer();
        return customerRepository.findById(customerId).get();
    }
}
