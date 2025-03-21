package com.willem.demo.services;

import com.willem.demo.entities.Order;
import com.willem.demo.mappers.OrderMapper;
import com.willem.demo.model.OrderDto;
import com.willem.demo.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author willem
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto saveOrder(OrderDto orderDto)
    {
        log.info("Saving order: {}", orderDto.getId());

        Order savedOrder = orderRepository.save(orderMapper.toEntity(orderDto));
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public boolean deleteOrder(Long id)
    {
        log.info("Attempting to delete order with ID: {}", id);
        if (orderRepository.existsById(id))
        {
            orderRepository.deleteById(id);
            log.info("Order with ID {} deleted successfully.", id);
            return true;
        }

        log.warn("Order with ID {} not found, delete operation failed.", id);
        return false;
    }

    @Override
    public List<OrderDto> findAllOrders()
    {
        log.info("Fetching all orders without pagination");

        // Get all orders and map them to DTOs
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OrderDto> findAllOrders(Pageable pageable)
    {
        log.info("Fetching orders with pagination: {}", pageable);

        return orderRepository.findAll(pageable)
                .map(orderMapper::toDto);
    }

    @Override
    public Optional<OrderDto> findOrderById(Long id)
    {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent())
        {
            log.info("Order with ID {} found.", id);
            return Optional.of(orderMapper.toDto(order.get()));
        }

        log.error("Order with ID {} not found.", id);
        throw new EntityNotFoundException("Order with ID " + id + " not found.");
    }

    @Override
    public List<OrderDto> findByCustomerId(Long customerId)
    {
        log.info("Fetching orders for customer ID {}", customerId);
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        log.info("Fetched {} orders for customer ID {}", orders.size(), customerId);
        return orders.stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
}
