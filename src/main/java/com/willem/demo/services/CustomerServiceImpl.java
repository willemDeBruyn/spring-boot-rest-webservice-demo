package com.willem.demo.services;

import com.willem.demo.entities.Customer;
import com.willem.demo.mappers.CustomerMapper;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
