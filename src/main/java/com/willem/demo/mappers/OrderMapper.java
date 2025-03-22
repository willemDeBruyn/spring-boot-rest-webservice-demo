package com.willem.demo.mappers;

import com.willem.demo.entities.Customer;
import com.willem.demo.entities.Order;
import com.willem.demo.model.CustomerDto;
import com.willem.demo.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * @author willem
 */
@Mapper(componentModel = "spring")
public interface OrderMapper
{
    @Mapping(target = "customer", ignore = true)
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);

    void updateOrderFromDto(OrderDto orderDto, @MappingTarget Order order);
}
