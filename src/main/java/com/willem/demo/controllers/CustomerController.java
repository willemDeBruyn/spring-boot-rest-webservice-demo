package com.willem.demo.controllers;

import com.willem.demo.model.CustomerDto;
import com.willem.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @author willem
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController
{
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDto)
    {
        return ResponseEntity.ok(customerService.saveCustomer(customerDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id)
    {
        boolean isDeleted = this.customerService.deleteCustomer(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers()
    {
        List<CustomerDto> customerDtos = customerService.findAllCustomers();
        return ResponseEntity.ok(customerDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id) {
        Optional<CustomerDto> customerDto = customerService.findCustomerById(id);
        return customerDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
