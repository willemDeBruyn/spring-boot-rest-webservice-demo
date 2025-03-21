package com.willem.demo.model;

import com.willem.demo.entities.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import java.time.LocalDateTime;

/**
 * @author willem
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderDto
{
    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Double price;

    private Order.OrderStatus status;
    private CustomerDto customer;
    private LocalDateTime orderDate;
}
