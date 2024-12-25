package com.osmel.bankingappbackend.dtos;

import lombok.Data;

@Data
public class TransfertOperationDto {
    private String accountSource;
    private String accountDestination;
    private double amount;

}
