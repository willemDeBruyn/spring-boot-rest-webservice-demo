package com.willem.demo.mappers;

import com.willem.demo.entities.Customer;
import com.willem.demo.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * @author willem
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper
{
    CustomerDto customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer(CustomerDto customerDto);
}
