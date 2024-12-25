package com.osmel.bankingappbackend.reppositories;

import com.osmel.bankingappbackend.entities.Account;
import com.osmel.bankingappbackend.entities.BankOperation;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface AccountRepo extends JpaRepository<Account,String> {
    List<Account> findByCustomerId(Long customerId);
}
