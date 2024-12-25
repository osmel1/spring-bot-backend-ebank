package com.osmel.bankingappbackend.dtos;

import com.osmel.bankingappbackend.enums.TyppeOperation;
import lombok.Data;

import java.util.Date;
@Data
public class BankOperationDto {
    private Long id ;
    private Date dateOperation;
    private Double amount;
    private TyppeOperation typpeOperation;
}
