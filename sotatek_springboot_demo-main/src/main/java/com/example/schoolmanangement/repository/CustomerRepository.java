package com.example.schoolmanangement.repository;


import com.example.schoolmanangement.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByPhone(String phone);

    @Query("""
            SELECT c FROM Customer c
            WHERE ( :phone IS NULL OR c.phone LIKE CONCAT('%',:phone, '%') )
            AND ( :name IS NULL OR c.name LIKE CONCAT('%',:name, '%') )
            AND ( :address IS NULL OR c.address LIKE CONCAT('%',:address, '%') )
            """)
    List<Customer> findCustomer(String phone, String name, String address);
}
