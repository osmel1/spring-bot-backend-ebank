package com.osmel.bankingappbackend.reppositories;

import com.osmel.bankingappbackend.entities.BankOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankOperationRepo extends JpaRepository<BankOperation,Long> {
    List<BankOperation> findAllByAccount_Id(String accountId);
    Page<BankOperation> findAllByAccount_Id(String accountId, Pageable pageable);
}
