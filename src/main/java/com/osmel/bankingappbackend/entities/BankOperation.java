package com.osmel.bankingappbackend.entities;

import com.osmel.bankingappbackend.enums.TyppeOperation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private Date dateOperation;
    private Double amount;
    private TyppeOperation typpeOperation;
    @ManyToOne
    private Account account;
}
