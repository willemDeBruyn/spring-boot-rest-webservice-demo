package com.willem.demo.services;

import com.willem.demo.model.CustomerDto;
import com.willem.demo.model.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author willem
 */
public interface CustomerService
{
    CustomerDto saveCustomer(CustomerDto customerDto);
    boolean deleteCustomer(Long id);

    List<CustomerDto> findAllCustomers();
    Page<CustomerDto> findAllCustomers(Pageable pageable);
    Optional<CustomerDto> findCustomerById(Long id);
}
