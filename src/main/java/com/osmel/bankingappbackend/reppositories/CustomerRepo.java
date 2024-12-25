package com.osmel.bankingappbackend.reppositories;

import com.osmel.bankingappbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Long> {
    //search by name
    List<Customer> findCustomerByNameContains(String name);
}
