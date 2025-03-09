package com.willem.demo.services;

import com.willem.demo.entities.Customer;
import com.willem.demo.mappers.CustomerMapper;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author willem
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService
{
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerDto saveCustomer(CustomerDto customerDto)
    {
        Customer savedCustomer = customerRepository.save(customerMapper.toEntity(customerDto));
        return customerMapper.toDto(savedCustomer);
    }

    @Override
    public boolean deleteCustomer(Long id)
    {
        if (customerRepository.existsById(id))
        {
            customerRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<CustomerDto> findAllCustomers()
    {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDto> findCustomerById(Long id)
    {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.map(customerMapper::toDto);
    }
}
