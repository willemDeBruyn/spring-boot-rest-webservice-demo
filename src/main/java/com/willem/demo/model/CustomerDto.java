package com.willem.demo.model;

import lombok.*;

import java.util.List;

/**
 * @author willem
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerDto
{
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String address;
    private List<OrderDto> orders;
}
