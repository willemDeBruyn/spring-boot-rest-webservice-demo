package com.willem.demo.controllers;

import com.willem.demo.model.CustomerDto;
import com.willem.demo.model.OrderDto;
import com.willem.demo.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<OrderDto> saveOrder(@Validated @RequestBody OrderDto orderDto)
    {
        OrderDto savedOrder = orderService.saveOrder(orderDto);
        return ResponseEntity.ok(savedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id)
    {
        boolean isDeleted = orderService.deleteOrder(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getAllOrders(@RequestParam(required = false) Integer page,
                                          @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
        {
            List<OrderDto> allOrders = orderService.findAllOrders();
            return ResponseEntity.ok(allOrders);
        }
        else
        {
            Pageable pageable = PageRequest.of(page, size);
            Page<OrderDto> ordersPage = orderService.findAllOrders(pageable);
            return ResponseEntity.ok(ordersPage);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") Long id)
    {
        Optional<OrderDto> orderDto = orderService.findOrderById(id);
        return orderDto
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomerId(@PathVariable("customerId") Long customerId)
    {
        List<OrderDto> orderDtos = orderService.findByCustomerId(customerId);
        if (orderDtos.isEmpty())
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(orderDtos);
    }
}
