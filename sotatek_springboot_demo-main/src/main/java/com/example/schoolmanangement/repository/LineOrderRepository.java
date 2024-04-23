package com.example.schoolmanangement.repository;


import com.example.schoolmanangement.entity.LineOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LineOrderRepository extends JpaRepository<LineOrder, Long> {

    @Query("""
            SELECT lo.customerId
            FROM Order o JOIN LineOrder lo ON o.id = lo.orderId
            GROUP BY lo.customerId
            ORDER BY SUM(o.totalMoney) DESC LIMIT 1
            """)
    Long findGoldCustomer();
}
