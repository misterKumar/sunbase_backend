package com.sunbase.customerApp.repository;

import com.sunbase.customerApp.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByUuid(String uuid);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByUuid(String uuid);
    void deleteByUuid(String uuid);
}
