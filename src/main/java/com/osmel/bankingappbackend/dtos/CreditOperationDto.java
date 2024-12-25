package com.osmel.bankingappbackend.dtos;

import lombok.Data;

@Data
public class CreditOperationDto {
    private String accountId;
    private double amount;
}
