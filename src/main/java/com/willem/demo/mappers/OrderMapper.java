package com.willem.demo.mappers;

import com.willem.demo.entities.Order;
import com.willem.demo.model.OrderDto;
import org.mapstruct.Mapper;

/**
 * @author willem
 */
@Mapper(componentModel = "spring")
public interface OrderMapper
{
    OrderDto orderToOrderDto(Order order);
    Order orderDtoToOrder(OrderDto orderDto);
}
