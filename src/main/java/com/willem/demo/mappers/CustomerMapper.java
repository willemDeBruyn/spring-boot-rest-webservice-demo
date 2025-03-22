package com.willem.demo.mappers;

import com.willem.demo.entities.Customer;
import com.willem.demo.model.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * @author willem
 */
@Mapper(componentModel = "spring", uses = { OrderMapper.class })
public interface CustomerMapper
{
    CustomerDto toDto(Customer customer);
    Customer toEntity(CustomerDto customerDto);

    // This method will update only non-null properties
    void updateCustomerFromDto(CustomerDto customerDto, @MappingTarget Customer customer);
}
