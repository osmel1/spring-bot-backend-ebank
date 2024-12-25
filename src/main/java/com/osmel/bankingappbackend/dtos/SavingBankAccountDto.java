package com.osmel.bankingappbackend.dtos;

import com.osmel.bankingappbackend.enums.StatusAccount;
import lombok.Data;
import java.util.Date;

@Data
public  class SavingBankAccountDto extends BankAccountDto  {
    private String id;
    private Date createdAt ;
    private Double balance;
    private StatusAccount statusAccount;
    private String currency;
    private CustomerDto customerDto;
    private Double interestRate;
}
