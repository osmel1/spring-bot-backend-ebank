package com.osmel.bankingappbackend.dtos;

import lombok.Data;

@Data
public class DebitOperationDto {
    private String accountId;
    private double amount;
}
