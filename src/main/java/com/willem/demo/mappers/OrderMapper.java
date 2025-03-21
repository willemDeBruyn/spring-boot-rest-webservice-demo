package com.willem.demo.mappers;

import com.willem.demo.entities.Order;
import com.willem.demo.model.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author willem
 */
@Mapper(componentModel = "spring")
public interface OrderMapper
{
    @Mapping(target = "customer", ignore = true)
    OrderDto toDto(Order order);
    Order toEntity(OrderDto orderDto);
}
