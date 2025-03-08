package com.willem.demo.services;


import com.willem.demo.model.CustomerDto;
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
    Optional<CustomerDto> findCustomerById(Long id);
}
