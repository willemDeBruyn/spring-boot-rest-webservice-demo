package com.willem.demo.model;

import com.willem.demo.entities.Order;
import lombok.*;
import java.time.LocalDateTime;

/**
 * @author willem
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDto
{
    private Long id;
    private String name;
    private String description;
    private LocalDateTime orderDate;
    private Double price;
    private Order.OrderStatus status;
    private CustomerDto customer;
}
