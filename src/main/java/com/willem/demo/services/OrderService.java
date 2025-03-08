package com.willem.demo.services;

import com.willem.demo.model.OrderDto;
import java.util.List;
import java.util.Optional;

/**
 * @author willem
 */
public interface OrderService
{
    OrderDto saveOrder(OrderDto order);
    boolean deleteOrder(Long id);

    List<OrderDto> findAllOrders();
    Optional<OrderDto> findOrderById(Long id);
    List<OrderDto> findByCustomerId(Long customerId);
}