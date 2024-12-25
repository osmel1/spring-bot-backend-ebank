package com.osmel.bankingappbackend.entities;

import com.osmel.bankingappbackend.enums.StatusAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class  Account {
    @Id
    private String id;
    private Date createdAt ;
    private Double balance;
    @Enumerated(value = EnumType.STRING)
    private StatusAccount statusAccount;
    private String currency;
    @ManyToOne
    private Customer customer;
    @OneToMany(mappedBy = "account" ,fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    private List<BankOperation> bankOperations;
}
