package com.willem.demo.services;

import com.willem.demo.model.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author willem
 */
public interface OrderService
{
    OrderDto saveOrder(OrderDto order);
    boolean deleteOrder(Long id);
    OrderDto updateOrder(Long id, OrderDto order);

    List<OrderDto> findAllOrders();
    Page<OrderDto> findAllOrders(Pageable pageable);
    Optional<OrderDto> findOrderById(Long id);
    List<OrderDto> findByCustomerId(Long customerId);
}