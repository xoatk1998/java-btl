package com.example.schoolmanangement.repository;


import com.example.schoolmanangement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long id);

    @Query("""
            SELECT i FROM Inventory i WHERE i.productId in :productIds
            """)
    List<Inventory> findByProductIds(List<Long> productIds);
}
