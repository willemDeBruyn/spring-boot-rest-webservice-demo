package com.willem.demo.services;

import com.willem.demo.entities.Customer;
import com.willem.demo.mappers.CustomerMapper;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author willem
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService
{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;


    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto)
    {
        log.info("Saving customer: {}", customerDto.getId());
        Customer savedCustomer = customerRepository.save(customerMapper.toEntity(customerDto));
        return customerMapper.toDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto)
    {
        log.info("Updating customer ID {}", id);
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        // Update non-null properties from the DTO into the existing entity
        customerMapper.updateCustomerFromDto(customerDto, existingCustomer);

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Customer updated  ID {}", id);
        return customerMapper.toDto(updatedCustomer);
    }

    @Override
    public boolean deleteCustomer(Long id)
    {
        log.info("Attempting to delete customer with ID: {}", id);
        if (customerRepository.existsById(id))
        {
            customerRepository.deleteById(id);
            log.info("Customer with ID {} deleted successfully.", id);
            return true;
        }

        log.warn("Customer with ID {} not found, delete operation failed.", id);
        return false;
    }

    @Override
    public List<CustomerDto> findAllCustomers()
    {
        log.info("Fetching all customers.");
        List<Customer> customers = customerRepository.findAll();
        log.info("Fetched {} customers.", customers.size());
        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CustomerDto> findAllCustomers(Pageable pageable)
    {
        log.info("Fetching orders with pagination: {}", pageable);

        return customerRepository.findAll(pageable)
                .map(customerMapper::toDto);
    }

    @Override
    public Optional<CustomerDto> findCustomerById(Long id)
    {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent())
        {
            log.info("Customer with ID {} found.", id);
            return Optional.of(customerMapper.toDto(customer.get()));
        }

        log.error("Customer with ID {} not found.", id);
        throw new EntityNotFoundException("Customer with ID " + id + " not found.");
    }
}
