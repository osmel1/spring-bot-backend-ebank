package com.osmel.bankingappbackend.mapper;

import com.osmel.bankingappbackend.dtos.*;
import com.osmel.bankingappbackend.entities.BankOperation;
import com.osmel.bankingappbackend.entities.CurrentAccount;
import com.osmel.bankingappbackend.entities.Customer;
import com.osmel.bankingappbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapper {
    public CustomerDto fromCustomer(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        BeanUtils.copyProperties(customer, customerDto);
        return customerDto;
    }
    public Customer fromCustomerDto(CustomerDto customerDto){
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDto, customer);
        return customer;
    }

    public CurrentAccount fromCurrentAccountDto(CurrentBankAccountDto currentAccountDto){
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentAccountDto, currentAccount);
        currentAccount.setCustomer(fromCustomerDto(currentAccountDto.getCustomerDto()));
        return currentAccount;
    }

    public CurrentBankAccountDto fromCurrentAccount(CurrentAccount currentAccount){
        CurrentBankAccountDto currentAccountDto = new CurrentBankAccountDto();
        BeanUtils.copyProperties(currentAccount, currentAccountDto);
        currentAccountDto.setCustomerDto(fromCustomer(currentAccount.getCustomer()));
        currentAccountDto.setType(CurrentAccount.class.getSimpleName());
        return currentAccountDto;
    }

    public SavingAccount fromSavingAccountDto(SavingBankAccountDto savingAccountDto){
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingAccountDto, savingAccount);
        savingAccount.setCustomer(fromCustomerDto(savingAccountDto.getCustomerDto()));
        return savingAccount;
    }

    public SavingBankAccountDto fromSavingAccount(SavingAccount savingAccount){
        SavingBankAccountDto savingAccountDto = new SavingBankAccountDto();
        BeanUtils.copyProperties(savingAccount, savingAccountDto);
        savingAccountDto.setCustomerDto(fromCustomer(savingAccount.getCustomer()));
        savingAccountDto.setType(SavingAccount.class.getSimpleName());
        return savingAccountDto;
    }

    public BankOperationDto fromBankOperation(BankOperation bankOperation){
        BankOperationDto bankOperationDto = new BankOperationDto();
        BeanUtils.copyProperties(bankOperation, bankOperationDto);
        return bankOperationDto;
    }

    public BankOperation fromBankOperationDto(BankOperationDto bankOperationDto){
        BankOperation bankOperation = new BankOperation();
        BeanUtils.copyProperties(bankOperationDto, bankOperation);
        return bankOperation;
    }

}
