package com.osmel.bankingappbackend.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AccountHistDto {
    private String accountID ;
    private double balance ;
    private int currentPage ;
    private int totalPages ;
    private int pageSize ;
    private List<BankOperationDto> bankOperationDtos ;

}
