package com.osmel.bankingappbackend.services;

import com.osmel.bankingappbackend.dtos.*;
import com.osmel.bankingappbackend.entities.*;
import com.osmel.bankingappbackend.enums.TyppeOperation;
import com.osmel.bankingappbackend.exceptions.AccountNotFoundException;
import com.osmel.bankingappbackend.exceptions.BalanceNotSufficientException;
import com.osmel.bankingappbackend.mapper.BankAccountMapper;
import com.osmel.bankingappbackend.reppositories.AccountRepo;
import com.osmel.bankingappbackend.reppositories.BankOperationRepo;
import com.osmel.bankingappbackend.reppositories.CustomerRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class BankService {
    private CustomerRepo customerRepo;
    private AccountRepo accountRepo;
    private BankOperationRepo bankOperationRepo;
    private BankAccountMapper bankAccountMapper;
    public List<CustomerDto> getListCustomers(){
        return customerRepo.findAll().stream().map(
                customer -> bankAccountMapper.fromCustomer(customer)
        ).toList();
    }

    public CustomerDto getCustomerById(Long id){
        return bankAccountMapper.fromCustomer(customerRepo.findById(id).get());
    }

    public CustomerDto saveCustomer(CustomerDto customerDto){
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        Customer customerSaved = customerRepo.save(customer);
        return bankAccountMapper.fromCustomer(customerSaved);
    }
    public CustomerDto updateCustomer(CustomerDto customerDto){
        Customer customer = bankAccountMapper.fromCustomerDto(customerDto);
        Customer customerSaved = customerRepo.save(customer);
        return bankAccountMapper.fromCustomer(customerSaved);
    }

    public void deleteCustomer(Long id){
        Customer customer = customerRepo.findById(id).orElse(null);
        if(customer == null){
            throw  new RuntimeException("Customer not found");
        }
        List<Account> accounts = accountRepo.findByCustomerId(id);
        accounts.forEach(
                account -> {
                    List<BankOperation> bankOperations = bankOperationRepo.findAllByAccount_Id(account.getId());
bankOperationRepo.deleteAll(bankOperations);
                }
        );
        accountRepo.deleteAll(accounts);

        customerRepo.deleteById(id);
    }
    public CurrentBankAccountDto saveAccount(double initialAmount , double overdraft , Long customerId){
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if(customer == null){
            throw  new RuntimeException("Customer not found");
        }
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialAmount);
        currentAccount.setOverDraft(overdraft);
        currentAccount.setCustomer(customer);
        CurrentAccount accountSaved = accountRepo.save(currentAccount);
        return bankAccountMapper.fromCurrentAccount(accountSaved);
    }
    private Account getAccountById(String accountId){
        return accountRepo.findById(accountId).orElse(null);
    }

    public SavingBankAccountDto saveSavingAccount(double initialAmount , double interestRate , Long customerId){
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if(customer == null){
            throw  new RuntimeException("Customer not found");
        }
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialAmount);
        savingAccount.setInterstRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount accountSaved = accountRepo.save(savingAccount);
        return bankAccountMapper.fromSavingAccount(accountSaved);
    }

    public BankAccountDto getBankAccountById(String id) throws AccountNotFoundException {
        Account bankAccount = accountRepo.findById(id).orElse(null);
        if(bankAccount == null){
            throw  new AccountNotFoundException("Account not found");
        }
        if(bankAccount instanceof CurrentAccount){
            return bankAccountMapper.fromCurrentAccount((CurrentAccount) bankAccount);
        }else{
            return bankAccountMapper.fromSavingAccount((SavingAccount) bankAccount);
        }
    }

    public void debit(String accountId , double amount) throws AccountNotFoundException, BalanceNotSufficientException {
        Account bankAccount = getAccountById(accountId);
        if(bankAccount == null){
            throw  new AccountNotFoundException("Account not found");
        }
        if (bankAccount.getBalance()<amount) {
            throw new BalanceNotSufficientException("Balance not sufficient");
        }
        BankOperation bankOperation = new BankOperation();
        bankOperation.setAmount(amount);
        bankOperation.setDateOperation(new Date());
        bankOperation.setAccount(bankAccount);
        bankOperation.setTyppeOperation(TyppeOperation.DEBIT);
        bankOperationRepo.save(bankOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        accountRepo.save(bankAccount);
    }

    public void credit(String accountId , double amount) throws AccountNotFoundException {
       Account bankAccount = getAccountById(accountId);
        if(bankAccount == null){
            throw  new AccountNotFoundException("Account not found");
        }
        BankOperation bankOperation = new BankOperation();
        bankOperation.setAmount(amount);
        bankOperation.setDateOperation(new Date());
        bankOperation.setAccount(bankAccount);
        bankOperation.setTyppeOperation(TyppeOperation.CREDIT);
        bankOperationRepo.save(bankOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        accountRepo.save(bankAccount);
    }

    public void transfert(String accountIdSource , String accountIdDestination , double amount) throws AccountNotFoundException, BalanceNotSufficientException {
        Account bankAccountSource = accountRepo.findById(accountIdSource).orElse(null);
        Account bankAccountDestination = accountRepo.findById(accountIdDestination).orElse(null);
        if(bankAccountSource == null || bankAccountDestination == null){
            //who is null
            String idNullAccount = (bankAccountSource == null) ? accountIdSource : accountIdDestination;
            throw new AccountNotFoundException("Account with id : "+idNullAccount+ " not found" );
        }
        debit(accountIdSource,amount);
        credit(accountIdDestination,amount);
    }

    public List<BankAccountDto> getAllAccounts(){
        return accountRepo.findAll().stream().map(
                account -> {
                    if(account instanceof CurrentAccount){
                        return bankAccountMapper.fromCurrentAccount((CurrentAccount) account);
                    }else{
                        return bankAccountMapper.fromSavingAccount((SavingAccount) account);
                    }
                }
        ).toList();
    }

    public List<BankOperationDto> getOperationsByAccount(String accountId){
        return bankOperationRepo.findAllByAccount_Id(accountId).stream().map(
                bankOperation -> bankAccountMapper.fromBankOperation(bankOperation)
        ).toList(
        );
    }

    public AccountHistDto getAccountHistory(String accountNumber , int page , int size) throws AccountNotFoundException {
        //find the account
        Account bankAccount = accountRepo.findById(accountNumber).orElse(null);
        if (bankAccount== null) {
            throw new AccountNotFoundException("Account not found");
        }
        //get the operations of an account with the id accountNumber
        Page<BankOperation> allByAccountId = bankOperationRepo.findAllByAccount_Id(accountNumber, PageRequest.of(page, size));
        AccountHistDto accountHistDto = new AccountHistDto();
        accountHistDto.setAccountID(accountNumber);
        accountHistDto.setBankOperationDtos(allByAccountId.getContent().stream().map(
                bankOperation -> bankAccountMapper.fromBankOperation(bankOperation)
        ).toList());
        accountHistDto.setTotalPages(allByAccountId.getTotalPages());
        accountHistDto.setBalance(bankAccount.getBalance());
        accountHistDto.setPageSize(size);
        accountHistDto.setCurrentPage(page);
        return accountHistDto;
    }

    public List<CustomerDto> searchCustomer(String name) {
        return customerRepo.findCustomerByNameContains(name).stream().map(
                customer -> bankAccountMapper.fromCustomer(customer)
        ).toList();
    }
    public List<BankAccountDto> searchAccountsByCustomerId(Long id){
        return accountRepo.findByCustomerId(id).stream().map(
                account -> {
                    if(account instanceof CurrentAccount){
                        return bankAccountMapper.fromCurrentAccount((CurrentAccount) account);
                    }else{
                        return bankAccountMapper.fromSavingAccount((SavingAccount) account);
                    }
                }
        ).toList();
    }
}
