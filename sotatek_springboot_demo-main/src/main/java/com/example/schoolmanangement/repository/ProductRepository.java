package com.example.schoolmanangement.repository;


import com.example.schoolmanangement.dto.response.ProductResponse;
import com.example.schoolmanangement.entity.Order;
import com.example.schoolmanangement.entity.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT NEW com.example.schoolmanangement.dto.response.ProductResponse(
                p.name,
                p.price AS price,
                i.stockQuantity
            ) FROM Product p JOIN Inventory i ON p.id = i.productId
             WHERE ( :price IS NULL OR p.price = :price )
             AND (:name IS NULL OR p.name = :name)
            """)
    List<ProductResponse> findProductByPriceAndName( BigDecimal price, String name, Sort sort);

    @Query("""
            SELECT p FROM Product p
            WHERE p.id IN :productIds
            """)
    List<Product> findByIdIn(List<Long> productIds);

    @Query("""
            SELECT NEW com.example.schoolmanangement.dto.response.ProductResponse(
                p.name,
                p.price AS price,
                i.stockQuantity
            ) FROM Product p JOIN Inventory i ON p.id = i.productId
            WHERE i.stockQuantity <= 3
            """)
    List<ProductResponse> findProductReport();
}
