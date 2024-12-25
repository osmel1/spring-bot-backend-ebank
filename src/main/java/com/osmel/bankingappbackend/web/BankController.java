package com.osmel.bankingappbackend.web;

import com.osmel.bankingappbackend.dtos.BankAccountDto;
import com.osmel.bankingappbackend.dtos.CustomerDto;
import com.osmel.bankingappbackend.entities.Customer;
import com.osmel.bankingappbackend.services.BankService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class BankController {
    private BankService bankService;
    @GetMapping("/customers")
    public List<CustomerDto> customerList(){
        return bankService.getListCustomers();
    }

    @GetMapping("/searchCustomer")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDto> searchCustomer(@RequestParam String name){
        return bankService.searchCustomer(name);
    }
    @GetMapping("/customer/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public CustomerDto getCustomerId(@PathVariable Long id){
        return bankService.getCustomerById(id);
    }
    @GetMapping("/customer/{id}/accounts")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<BankAccountDto> getAccountsByCustomerId(@PathVariable Long id){
        return bankService.searchAccountsByCustomerId(id);
    }
    @PostMapping("/customer")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDto saveCustomer(@RequestBody CustomerDto customerDto){
        return bankService.saveCustomer(customerDto);
    }

    @PutMapping("/customer/{id}")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDto updateCustomer(@RequestBody CustomerDto customerDto , @PathVariable Long id){
        customerDto.setId(id);
        return bankService.updateCustomer(customerDto);
    }

    @DeleteMapping("/customer/{id}")
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long id){
        System.out.println("Delete customer with id: " + id);

        bankService.deleteCustomer(id);
    }
}
