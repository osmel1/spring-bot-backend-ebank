package com.osmel.bankingappbackend.web;

import com.osmel.bankingappbackend.dtos.*;
import com.osmel.bankingappbackend.entities.BankOperation;
import com.osmel.bankingappbackend.exceptions.AccountNotFoundException;
import com.osmel.bankingappbackend.exceptions.BalanceNotSufficientException;
import com.osmel.bankingappbackend.services.BankService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccoutnController {
    private BankService bankService;

    public BankAccoutnController(BankService bankService) {
        this.bankService = bankService;
    }
    @GetMapping("/accounts/{accountNumber}")

    public BankAccountDto getBankAccountByNumber(@PathVariable String accountNumber) throws AccountNotFoundException {
        return bankService.getBankAccountById(accountNumber);
    }

    @GetMapping("/accounts")
    public  List<BankAccountDto> getBankAccounts() {
        return bankService.getAllAccounts();
    }

    @GetMapping("/accounts/{accountNumber}/operations")
    public List<BankOperationDto> getBankOperations(@PathVariable String accountNumber)  {
        return bankService.getOperationsByAccount(accountNumber);
    }

    @GetMapping("/accounts/{accountNumber}/pageOperations")
    public AccountHistDto getHistory(@PathVariable String accountNumber ,
                                     @RequestParam(name = "page" ,defaultValue = "0") int page ,
                                     @RequestParam(name = "size" , defaultValue = "5") int size) throws AccountNotFoundException {
        return bankService.getAccountHistory(accountNumber,page,size);
    }

    @PostMapping("/accounts/credit")
    public CreditOperationDto credit(@RequestBody CreditOperationDto creditOperationDto) throws AccountNotFoundException {
        bankService.credit(creditOperationDto.getAccountId(),creditOperationDto.getAmount());
        return creditOperationDto;
    }

    @PostMapping("/accounts/debit")
    public DebitOperationDto debit(@RequestBody DebitOperationDto debitOperationDto) throws AccountNotFoundException, BalanceNotSufficientException {
        bankService.debit(debitOperationDto.getAccountId(),debitOperationDto.getAmount());
        return debitOperationDto;
    }
    @PostMapping("/accounts/transfer")
    public TransfertOperationDto transfer(@RequestBody TransfertOperationDto transfertOperationDto) throws AccountNotFoundException, BalanceNotSufficientException {
        bankService.transfert(transfertOperationDto.getAccountSource(),transfertOperationDto.getAccountDestination(),transfertOperationDto.getAmount());
        return transfertOperationDto;
    }



}
