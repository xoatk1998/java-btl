package com.example.schoolmanangement.service.impl;

import com.example.schoolmanangement.dto.request.common.CreateOrderRequest;
import com.example.schoolmanangement.dto.request.common.ProductInOrder;
import com.example.schoolmanangement.entity.*;
import com.example.schoolmanangement.exception.ApiException;
import com.example.schoolmanangement.exception.dto.BaseErrorResponse;
import com.example.schoolmanangement.repository.*;
import com.example.schoolmanangement.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final InventoryRepository inventoryRepository;

    private final CustomerRepository customerRepository;

    private final OrderRepository orderRepository;

    private final LineOrderRepository lineOrderRepository;

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public String createOrder(CreateOrderRequest request) {
        if(!customerRepository.existsById(request.getCustomerId()))
            throw new ApiException(new BaseErrorResponse(106, "Customer does not exist"));

        List<Long> productIds = request.getProductsOrder().stream().map(ProductInOrder::getProductId).toList();
        List<Inventory> inventories = inventoryRepository.findByProductIds(productIds);
        Map<Long, Inventory> mapProductIdToQuantity = new HashMap<>();
        inventories.forEach(
            i -> mapProductIdToQuantity.put(i.getProductId(), i)
        );

        List<Product> products = productRepository.findByIdIn(productIds);
        Map<Long, Product> mapProductId = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        for (ProductInOrder productInOrder : request.getProductsOrder()){
            if(mapProductIdToQuantity.containsKey(productInOrder.getProductId())){
                if(mapProductIdToQuantity.get(productInOrder.getProductId()).getStockQuantity() > productInOrder.getQuantity()){
                    Order order = Order.builder()
                            .customerId(request.getCustomerId())
                            .issueDate(Instant.now())
                            .totalMoney(mapProductId.get(productInOrder.getProductId()).getPrice().multiply(BigDecimal.valueOf(productInOrder.getQuantity())))
                            .build();
                    orderRepository.save(order);

                    LineOrder lineOrder = LineOrder.builder()
                            .orderId(order.getId())
                            .customerId(request.getCustomerId())
                            .productId(productInOrder.getProductId())
                            .quantity(productInOrder.getQuantity())
                            .build();
                    lineOrderRepository.save(lineOrder);

                    Inventory inventory = mapProductIdToQuantity.get(productInOrder.getProductId());
                    inventory.setStockQuantity(inventory.getStockQuantity() - productInOrder.getQuantity());
                    inventory.setUpdatedDate(Instant.now());
                    inventoryRepository.save(inventory);
                } else {
                    throw new ApiException(new BaseErrorResponse(1001, "Out of stock quantity"));
                }
            }
        }

        return "Create orders successful";
    }
}
