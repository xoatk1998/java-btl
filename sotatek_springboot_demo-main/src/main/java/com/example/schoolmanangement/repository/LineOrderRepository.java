package com.example.schoolmanangement.repository;


import com.example.schoolmanangement.entity.LineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LineOrderRepository extends JpaRepository<LineOrder, Long> {

    @Query("""
            SELECT o.customerId
            FROM Order o
            GROUP BY o.customerId
            ORDER BY SUM(o.totalMoney) DESC LIMIT 1
            """)
    Long findGoldCustomer();
}
