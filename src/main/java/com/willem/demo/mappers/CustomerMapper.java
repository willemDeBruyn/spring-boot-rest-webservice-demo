package com.willem.demo.mappers;

import com.willem.demo.entities.Customer;
import com.willem.demo.model.CustomerDto;
import org.mapstruct.Mapper;

/**
 * @author willem
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface CustomerMapper
{
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);
}
