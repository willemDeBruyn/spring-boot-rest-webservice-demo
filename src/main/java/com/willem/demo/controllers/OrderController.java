package com.willem.demo.controllers;

import com.willem.demo.model.OrderDto;
import com.willem.demo.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * @author willem
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController
{
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDto> saveOrder(@RequestBody OrderDto orderDto)
    {
        return ResponseEntity.ok(orderService.saveOrder(orderDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id)
    {
        boolean isDeleted = orderService.deleteOrder(id);
        if (isDeleted)
        {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders()
    {
        List<OrderDto> orderDtos = orderService.findAllOrders();
        return ResponseEntity.ok(orderDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<OrderDto>> getOrderById(@PathVariable("id") Long id)
    {
        Optional<OrderDto> orderDto = orderService.findOrderById(id);

        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomerId(@PathVariable("customerId") Long customerId)
    {
        List<OrderDto> orderDtos = orderService.findByCustomerId(customerId);
        return ResponseEntity.ok(orderDtos);
    }
}
