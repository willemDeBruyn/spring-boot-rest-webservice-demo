package com.willem.demo.controllers;

import com.willem.demo.model.CustomerDto;
import com.willem.demo.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<CustomerDto> saveCustomer(@Validated @RequestBody CustomerDto customerDto)
    {
        return ResponseEntity.ok(customerService.saveCustomer(customerDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id, CustomerDto customerDto)
    {
        CustomerDto customerDtoUpdated = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(customerDtoUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long id)
    {
        boolean isDeleted = this.customerService.deleteCustomer(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size)
    {
        if (page == null || size == null)
        {
            List<CustomerDto> allCustomers = customerService.findAllCustomers();
            return ResponseEntity.ok(allCustomers);
        }
        else
        {
            Pageable pageable = PageRequest.of(page, size);
            Page<CustomerDto> ordersPage = customerService.findAllCustomers(pageable);
            return ResponseEntity.ok(ordersPage);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("id") Long id)
    {
        Optional<CustomerDto> customerDto = customerService.findCustomerById(id);
        return customerDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
