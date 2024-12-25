package com.osmel.bankingappbackend;

import com.osmel.bankingappbackend.entities.*;
import com.osmel.bankingappbackend.enums.StatusAccount;
import com.osmel.bankingappbackend.enums.TyppeOperation;
import com.osmel.bankingappbackend.reppositories.AccountRepo;
import com.osmel.bankingappbackend.reppositories.BankOperationRepo;
import com.osmel.bankingappbackend.reppositories.CustomerRepo;
import com.osmel.bankingappbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankingAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingAppBackendApplication.class, args);
    }



    @Bean
    CommandLineRunner commandLineRunner(CustomerRepo customerRepo, AccountRepo accountRepo, BankOperationRepo bankOperationRepo, BankService bankService){
      return args -> {
          Stream.of("Oussama","Hatim" ,"Omar" ,"Abdelali" , "Amine").forEach(
                  name->{
                      Customer customer =new Customer();
                      customer.setName(name);
                      customer.setEmail(name+"@gmail.com");
                      customerRepo.save(customer);
                  }
          );

          customerRepo.findAll().forEach(
                  customer -> {
                      CurrentAccount currentAccount = new CurrentAccount();
                      currentAccount.setId(UUID.randomUUID().toString());
                      currentAccount.setBalance(8000.0);
                      currentAccount.setOverDraft(200.1);
                      currentAccount.setStatusAccount(StatusAccount.ACCTIVATED);
                      currentAccount.setCurrency("MAD");
                      currentAccount.setCreatedAt(new Date());
                      currentAccount.setCustomer(customer);
                      accountRepo.save(currentAccount);
                      SavingAccount savingAccount = new SavingAccount();
                      savingAccount.setId(UUID.randomUUID().toString());
                      savingAccount.setBalance(10000.0);
                      savingAccount.setInterstRate(2.1);
                      savingAccount.setStatusAccount(StatusAccount.ACCTIVATED);
                      savingAccount.setCurrency("MAD");
                      savingAccount.setCreatedAt(new Date());
                      savingAccount.setCustomer(customer);
                      accountRepo.save(savingAccount);
                  }
          );

          accountRepo.findAll().forEach(
                  account -> {
                      for (int i = 0; i <10 ; i++) {
                          BankOperation bankOperation=new BankOperation();
                          bankOperation.setDateOperation(new Date());
                          bankOperation.setAmount(Math.random()*12000);
                          bankOperation.setTyppeOperation(Math.random()>0.5? TyppeOperation.CREDIT: TyppeOperation.DEBIT);
                          bankOperation.setAccount(account);
                          bankOperationRepo.save(bankOperation);
                      }
                  }
          );



      };
    };


}
