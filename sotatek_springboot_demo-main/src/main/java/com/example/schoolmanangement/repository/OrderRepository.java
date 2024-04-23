package com.example.schoolmanangement.repository;


import com.example.schoolmanangement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
            SELECT SUM(o.totalMoney) FROM Order o
            WHERE o.issueDate BETWEEN :from AND :to
            """)
    BigDecimal findRevenue(Instant from, Instant to);
}
