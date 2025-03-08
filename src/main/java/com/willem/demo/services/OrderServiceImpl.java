package com.willem.demo.services;

import com.willem.demo.entities.Order;
import com.willem.demo.mappers.OrderMapper;
import com.willem.demo.model.OrderDto;
import com.willem.demo.repositories.OrderRepository;
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
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDto saveOrder(OrderDto orderDto)
    {
        Order savedOrder = orderRepository.save(orderMapper.orderDtoToOrder(orderDto));
        return orderMapper.orderToOrderDto(savedOrder);
    }

    @Override
    public boolean deleteOrder(Long id)
    {
        if (orderRepository.existsById(id))
        {
            orderRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public List<OrderDto> findAllOrders()
    {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<OrderDto> findOrderById(Long id)
    {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(orderMapper::orderToOrderDto);
    }

    @Override
    public List<OrderDto> findByCustomerId(Long customerId)
    {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(orderMapper::orderToOrderDto)
                .collect(Collectors.toList());
    }


}
